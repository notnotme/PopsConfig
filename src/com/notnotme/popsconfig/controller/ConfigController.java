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
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author romain
 *
 * Extends GamePad and use the Singleton pattern to make it available
 * trougth all the application.
 *
 * You can register a OnChangeListener to be notified each time
 * a data is changed inside the GamePad model.
 */
public final class ConfigController {

	private final static String TAG = ConfigController.class.getSimpleName();

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
	}

	private final ArrayList<OnChangeListener> mChangeListeners;
	private boolean mSaved;

	private final GamePad mGamePad;
	private final Screen mScreen;
	private DiscLoading mDiscLoading;
	private SoundVolume mSoundVolume;

	private ConfigController() {
		mSaved = false;
		mChangeListeners = new ArrayList<>();

		mGamePad = new GamePad();
		mScreen = new Screen();
		mDiscLoading = DiscLoading.NORMAL;
		mSoundVolume = SoundVolume.NORMAL;
	}

	public void saveConfig(File file) {
		Logger.getLogger(TAG).log(
				Level.INFO, "saveConfig() file: {0}",
				new Object[]{file.getPath()});

		// TODO: Write in database or file...

		mSaved = true;
		mChangeListeners.stream().forEach((onChangeListener) -> {
			onChangeListener.onSaved();
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

	public void assign(VitaButton vitaButton, PsxButton psxButton) throws Exception {
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
