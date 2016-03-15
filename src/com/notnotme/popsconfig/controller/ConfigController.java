package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.model.DiscLoading;
import com.notnotme.popsconfig.model.SoundVolume;
import com.notnotme.popsconfig.model.gamepad.PsxTouchButton;
import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import com.notnotme.popsconfig.model.gamepad.GamePadMode;
import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import com.notnotme.popsconfig.model.gamepad.GamePad;
import com.notnotme.popsconfig.model.gamepad.PsxButton;
import com.notnotme.popsconfig.model.gamepad.VitaButton;
import com.notnotme.popsconfig.model.gamepad.VitaTouchButton;
import com.notnotme.popsconfig.model.screen.Screen;
import com.notnotme.popsconfig.model.screen.ScreenFilter;
import com.notnotme.popsconfig.model.screen.ScreenMode;
import com.sun.javafx.geom.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author romain
 *
 * Use the Singleton pattern to make it available
 * trougth all the application.
 *
 * You can register a OnChangeListener to be notified each time
 * a data is changed inside the internal datas
 */
public final class ConfigController {

	private final static String TAG = ConfigController.class.getSimpleName();
	private final static int CONFIG_MAGIC_1 = 0x53504F50;
	private final static int CONFIG_MAGIC_2 = 0x4746432E;
	private final static int CONFIG_VERSION = 0x00000005;

	private static ConfigController sINSTANCE;
	public static ConfigController getInstance() {
		if (sINSTANCE == null) {
			sINSTANCE = new ConfigController();
		}

		return sINSTANCE;
	}

	/**
	 * Interface called when the model change or is saved
	 */
	public interface OnChangeListener {
		void onChanged();
		void onSaved();
		void onLoaded();
	}

	private final ArrayList<OnChangeListener> mChangeListeners;
	private boolean mSaved;

	private final GamePad mGamePad;
	private final Screen mScreen;
	private int mDiscNumber;
	private DiscLoading mDiscLoading;
	private SoundVolume mSoundVolume;

	private ConfigController() {
		mSaved = false;
		mChangeListeners = new ArrayList<>();

		mGamePad = new GamePad();
		mScreen = new Screen();
		mDiscNumber = 0;
		mDiscLoading = DiscLoading.NORMAL;
		mSoundVolume = SoundVolume.NORMAL;
	}

	public void saveConfig(File file) throws Exception {
		Logger.getLogger(TAG).log(
				Level.INFO, "saveConfig() file: {0}",
				new Object[]{file.getPath()});

		// Write __sce_menuinfo struct into a buffer
		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		bb.putInt(CONFIG_MAGIC_1); // header
		bb.putInt(CONFIG_MAGIC_2); // header
		bb.putInt(CONFIG_VERSION); // header

		bb.putInt(mDiscNumber); // disc number
		bb.putInt(mDiscLoading.ordinal()); // disc
		bb.putInt(mSoundVolume.ordinal()); // volume
		bb.putInt(mGamePad.getPort().ordinal()); // gamepad port
		bb.putShort((short) mGamePad.getMode().ordinal()); // gamepad mode
		bb.putShort((short) mGamePad.getMapping().ordinal()); // gamepad mapping
		bb.putInt(mScreen.getFilter().ordinal()); // screen filter
		bb.putInt(mScreen.getMode().ordinal()); // screenmode

		Rectangle screenSize = mScreen.getCustomSize();
		bb.putInt(screenSize.x); // screen X
		bb.putInt(screenSize.y); // screen Y
		bb.putInt(screenSize.width); // screen W
		bb.putInt(screenSize.height); // screen H

		// game pad custom keys & touch
		for (VitaButton button : VitaButton.values()) {
			bb.put((byte) get(button).ordinal());
		}
		for (VitaTouchButton button : VitaTouchButton.values()) {
			bb.put((byte) get(button).ordinal());
		}

		// Save __sce_menuinfo buffer into a file
		try (FileOutputStream os = new FileOutputStream(file)) {
			os.write(bb.array());
			os.flush();
			os.close();
		}

		mSaved = true;
		mChangeListeners.stream().forEach((onChangeListener) -> {
			onChangeListener.onSaved();
		});
	}

	public void loadConfig(File file) throws Exception {
		Logger.getLogger(TAG).log(
				Level.INFO, "loadConfig() file: {0}",
				new Object[]{file.getPath()});

		// load __sce_menuinfo buffer into a buffer
		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.order(ByteOrder.LITTLE_ENDIAN);
		try (FileInputStream is = new FileInputStream(file)) {
			byte content[] = new byte[1024];
			is.read(content);
			bb.put(content);
			bb.position(0);

			is.close();
		}

		// Load data from the buffer
		if(bb.getInt() != CONFIG_MAGIC_1 || bb.getInt() != CONFIG_MAGIC_2) {
			throw new Exception("Not a Pops __sce_menuinfo file");
		}

		if (bb.getInt() != CONFIG_VERSION) {
			throw new Exception("Version mismatch");
		}

		mDiscNumber = bb.getInt();
		mDiscLoading = DiscLoading.values()[bb.getInt()];
		mSoundVolume = SoundVolume.values()[bb.getInt()];
		mGamePad.setPort(GamePadPort.values()[bb.getInt()]);
		mGamePad.setMode(GamePadMode.values()[bb.getShort()]);
		mGamePad.setMapping(GamePadMapping.values()[bb.getShort()]);
		mScreen.setFilter(ScreenFilter.values()[bb.getInt()]);
		mScreen.setMode(ScreenMode.values()[bb.getInt()]);

		Rectangle screen = new Rectangle();
		screen.x = bb.getInt(); // screen X
		screen.y = bb.getInt(); // screen Y
		screen.width = bb.getInt(); // screen W
		screen.height = bb.getInt(); // screen H
		mScreen.setCustomSize(screen);

		// game pad custom keys & touch
		PsxButton buttons[] = PsxButton.values();
		for (VitaButton button : VitaButton.values()) {
			mGamePad.assign(button, buttons[bb.get()]);
		}
		PsxTouchButton touchButtons[] = PsxTouchButton.values();
		for (VitaTouchButton button : VitaTouchButton.values()) {
			mGamePad.assign(button, touchButtons[bb.get()]);
		}

		mSaved = true;
		mChangeListeners.stream().forEach((onChangeListener) -> {
			onChangeListener.onLoaded();
		});
	}

	public void addListener(OnChangeListener listener) {
		mChangeListeners.add(listener);
	}

	public void removeListener(OnChangeListener listener) {
		mChangeListeners.remove(listener);
	}

	public boolean isSaved() {
		return mSaved;
	}

	public void setGamePadPort(GamePadPort port) {
		if (port == mGamePad.getPort()) return;

		mGamePad.setPort(port);
		invalidateChange();
	}

	public GamePadPort getGamePadPort() {
		return mGamePad.getPort();
	}

	public void setGamePadMode(GamePadMode mode) {
		if (mode == mGamePad.getMode()) return;

		mGamePad.setMode(mode);
		invalidateChange();
	}

	public GamePadMode getGamePadMode() {
		return mGamePad.getMode();
	}

	public void setGamePadMapping(GamePadMapping controls) {
		if (controls == mGamePad.getMapping()) return;

		mGamePad.setMapping(controls);
		invalidateChange();
	}

	public GamePadMapping getGamePadMapping() {
		return mGamePad.getMapping();
	}

	public void assign(VitaButton vitaButton, PsxButton psxButton) {
		if (mGamePad.get(vitaButton) == psxButton) return;

		mGamePad.assign(vitaButton, psxButton);
		invalidateChange();
	}

	public void assign(VitaTouchButton vitaTouchButton, PsxTouchButton psxTouchButton) throws Exception {
		if (mGamePad.get(vitaTouchButton) == psxTouchButton) return;

		mGamePad.assign(vitaTouchButton, psxTouchButton);
		invalidateChange();
	}

	public PsxButton get(VitaButton vitaButton) {
		return mGamePad.get(vitaButton);
	}

	public PsxTouchButton get(VitaTouchButton vitaButton) {
		return mGamePad.get(vitaButton);
	}

	public void setScreenMode(ScreenMode screenMode) {
		if (mScreen.getMode() == screenMode) return;

		mScreen.setMode(screenMode);
		invalidateChange();
	}

	public ScreenMode getScreenMode() {
		return mScreen.getMode();
	}

	public void setScreenFilter(ScreenFilter screenFilter) {
		if (mScreen.getFilter() == screenFilter) return;

		mScreen.setFilter(screenFilter);
		invalidateChange();
	}

	public ScreenFilter getScreenFilter() {
		return mScreen.getFilter();
	}

	public void setDiscLoading(DiscLoading discLoading) {
		if (mDiscLoading == discLoading) return;

		mDiscLoading = discLoading;
		invalidateChange();
	}

	public DiscLoading getDiscLoading() {
		return mDiscLoading;
	}

	public void setSoundVolume(SoundVolume soundVolume) {
		if (mSoundVolume == soundVolume) return;

		mSoundVolume = soundVolume;
		invalidateChange();
	}

	public SoundVolume getSoundVolume() {
		return mSoundVolume;
	}

	public void setScreenSize(Rectangle rect) {
		if (mScreen.getCustomSize().equals(rect)) return;

		mScreen.setCustomSize(rect);
		invalidateChange();
	}

	public Rectangle getScreenSize() {
		return mScreen.getCustomSize();
	}

	public void setDiscNumber(int discNumber) {
		if (mDiscNumber == discNumber) return;

		mDiscNumber = discNumber;
		invalidateChange();
	}

	public int getDiscNumber() {
		return mDiscNumber;
	}

	/**
	 * Notify all listener of the changes
	 */
	private void invalidateChange() {
		mSaved = false;
		mChangeListeners.stream().forEach((onChangeListener) -> {
			onChangeListener.onChanged();
		});
	}

}
