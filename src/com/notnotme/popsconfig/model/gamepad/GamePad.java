package com.notnotme.popsconfig.model.gamepad;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

/**
 * @author romain
 */
public class GamePad {

	protected final PsxButton mButtonConfig[];
	protected final PsxTouchButton mTouchConfig[];

	protected GamePadMapping mControls;
	protected GamePadMode mControllerMode;
	protected GamePadPort mControllerPort;

	public GamePad() {
		int count = VitaButton.values().length;
		mButtonConfig = new PsxButton[VitaButton.values().length];
		for (VitaButton button : VitaButton.values()) {
			mButtonConfig[button.ordinal()] = PsxButton.UNUSED;
		}
		mTouchConfig = new PsxTouchButton[VitaTouchButton.values().length];
		for (VitaTouchButton button : VitaTouchButton.values()) {
			mTouchConfig[button.ordinal()] = PsxTouchButton.UNUSED;
		}

		mControls = GamePadMapping.DEFAULT;
		mControllerMode = GamePadMode.NUMERIC;
		mControllerPort = GamePadPort.PORT_1;
	}

	public void assign(VitaTouchButton vitaTouchButton, PsxTouchButton psxTouchButton) throws Exception {
		switch(vitaTouchButton) {
			case REAR_TOUCH_BOTTOM_LEFT:
			case REAR_TOUCH_BOTTOM_RIGHT:
			case REAR_TOUCH_UPPER_LEFT:
			case REAR_TOUCH_UPPER_RIGHT:
				switch (psxTouchButton) {
					case L1:
					case L2:
					case L3:
					case R1:
					case R2:
					case R3:
					case UNUSED:
						// allowed
						mTouchConfig[vitaTouchButton.ordinal()] = psxTouchButton;
						break;
					default:
						// disallowed
						throw new Exception("Cannot assign rear touch with something else that R/L 1,2,3");
				}
				break;
		}
	}

	/**
	 * Assign a ps vita virtual control with a ps one controle
	 * @param vitaButton The vita button to bind
	 * @param psxButton The ps one button to bind
	 * @throws Exception If the binding cannot be done
	 *
	 * It return false if you do a mistake in the mapping. The psone emu
	 * in psvita allow only some binding for retro touch (L and R 1,2,3)
	 * and some other for front touch (all but L1+R1 and L2+R2)
	 */
	public void assign(VitaButton vitaButton, PsxButton psxButton) throws Exception {
		mButtonConfig[vitaButton.ordinal()] = psxButton;
	}

	public List<Pair<VitaButton, PsxTouchButton>> getButtonsConfig() {
		ArrayList<Pair<VitaButton, PsxTouchButton>> config = new ArrayList<>();

		for (VitaButton button : VitaButton.values()) {
			config.add(new Pair(button, mButtonConfig[button.ordinal()]));
		}

		return config;
	}


	public void setControls(GamePadMapping controls) {
		mControls = controls;
	}

	public void setControllerMode(GamePadMode mode) {
		mControllerMode = mode;
	}

	public void setControllerPort(GamePadPort port) {
		mControllerPort = port;
	}

	public GamePadMapping getControls() {
		return mControls;
	}

	public GamePadMode getControllerMode() {
		return mControllerMode;
	}

	public GamePadPort getControllerPort() {
		return mControllerPort;
	}

}
