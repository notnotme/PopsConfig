package com.notnotme.popsconfig.controller;

import com.notnotme.popsconfig.model.gamepad.PsxTouchButton;
import com.notnotme.popsconfig.model.gamepad.GamePadMapping;
import com.notnotme.popsconfig.model.gamepad.GamePadMode;
import com.notnotme.popsconfig.model.gamepad.GamePadPort;
import com.notnotme.popsconfig.model.gamepad.GamePad;
import com.notnotme.popsconfig.model.gamepad.PsxButton;
import com.notnotme.popsconfig.model.gamepad.VitaButton;
import com.notnotme.popsconfig.model.gamepad.VitaTouchButton;
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
 * You can register a OnPadChangeListener to be notified each time
 * a data is changed inside the GamePad model.
 */
public class GamePadController extends GamePad {

	private final static String TAG = GamePadController.class.getSimpleName();

	private static GamePadController sINSTANCE;
	public static GamePadController getInstance() {
		if (sINSTANCE == null) {
			sINSTANCE = new GamePadController();
		}

		return sINSTANCE;
	}

	/**
	 * Interface called when the model change or is saved
	 */
	public interface OnPadChangeListener {
		void onChanged();
		void onSaved();
	}

	private final ArrayList<OnPadChangeListener> mPadChangeListeners;
	private boolean mSaved;

	private GamePadController() {
		mSaved = false;
		mPadChangeListeners = new ArrayList<>();
	}

	public void savePad(File file) {
		Logger.getLogger(TAG).log(
				Level.INFO, "savePad() file: {0}",
				new Object[]{file.getPath()});

		// TODO: Write in database or file...

		mSaved = true;
		mPadChangeListeners.stream().forEach((onPadChangeListener) -> {
			onPadChangeListener.onSaved();
		});
	}

	public void addListener(OnPadChangeListener listener) {
		mPadChangeListeners.add(listener);
	}

	public void removeListener(OnPadChangeListener listener) {
		mPadChangeListeners.remove(listener);
	}

	public boolean isSaved() {
		return mSaved;
	}

	@Override
	public void setControllerPort(GamePadPort port) {
		if (port == getControllerPort()) return;

		super.setControllerPort(port);
		mSaved = false;
		invalidateChange();
	}

	@Override
	public void setControllerMode(GamePadMode mode) {
		if (mode == getControllerMode()) return;

		super.setControllerMode(mode);
		mSaved = false;
		invalidateChange();
	}

	@Override
	public void setControls(GamePadMapping controls) {
		if (controls == getControls()) return;

		super.setControls(controls);
		mSaved = false;
		invalidateChange();
	}

	@Override
	public void assign(VitaButton vitaButton, PsxButton psxButton) throws Exception {
		if (mButtonConfig[vitaButton.ordinal()] == psxButton) return;

		super.assign(vitaButton, psxButton);
		mSaved = false;
		invalidateChange();
	}

	@Override
	public void assign(VitaTouchButton vitaTouchButton, PsxTouchButton psxTouchButton) throws Exception {
		if (mTouchConfig[vitaTouchButton.ordinal()] == psxTouchButton) return;

		super.assign(vitaTouchButton, psxTouchButton);
		mSaved = false;
		invalidateChange();
	}


	/**
	 * Notify all listener of the changes
	 */
	private void invalidateChange() {
		mPadChangeListeners.stream().forEach((onPadChangeListener) -> {
			onPadChangeListener.onChanged();
		});
	}

}
