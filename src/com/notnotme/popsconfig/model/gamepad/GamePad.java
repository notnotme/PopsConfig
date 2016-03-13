package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public final class GamePad {

	protected final PsxButton mButtonConfig[];
	protected final PsxTouchButton mTouchConfig[];

	protected GamePadMapping mGamePadMapping;
	protected GamePadMode mControllerMode;
	protected GamePadPort mControllerPort;

	public GamePad() {
		int count = VitaButton.values().length;
		mButtonConfig = new PsxButton[VitaButton.values().length];
		mTouchConfig = new PsxTouchButton[VitaTouchButton.values().length];

		// default value
		mGamePadMapping = GamePadMapping.DEFAULT;
		mControllerMode = GamePadMode.NUMERIC;
		mControllerPort = GamePadPort.PORT_1;

		assign(VitaButton.RIGHT, PsxButton.RIGHT);
		assign(VitaButton.LEFT, PsxButton.LEFT);
		assign(VitaButton.UP, PsxButton.UP);
		assign(VitaButton.DOWN, PsxButton.DOWN);
		assign(VitaButton.TRIANGLE, PsxButton.TRIANGLE);
		assign(VitaButton.CIRCLE, PsxButton.CIRCLE);
		assign(VitaButton.CROSS, PsxButton.CROSS);
		assign(VitaButton.SQUARE, PsxButton.SQUARE);
		assign(VitaButton.L, PsxButton.L1);
		assign(VitaButton.R, PsxButton.R1);
		assign(VitaButton.LEFT_ANALOG_LEFT, PsxButton.UNUSED);
		assign(VitaButton.LEFT_LANALOG_RIGHT, PsxButton.UNUSED);
		assign(VitaButton.LEFT_ANALOG_UP, PsxButton.UNUSED);
		assign(VitaButton.LEFT_ANALOG_DOWN, PsxButton.UNUSED);
		assign(VitaButton.RIGHT_ANALOG_LEFT, PsxButton.UNUSED);
		assign(VitaButton.RIGHT_ANALOG_RIGHT, PsxButton.UNUSED);
		assign(VitaButton.RIGHT_ANALOG_UP, PsxButton.UNUSED);
		assign(VitaButton.RIGHT_ANALOG_DOWN, PsxButton.UNUSED);

		for (VitaTouchButton button : VitaTouchButton.values()) {
			mTouchConfig[button.ordinal()] = PsxTouchButton.UNUSED;
		}
	}

	/**
	 * Assign a ps vita virtual control with a ps one controle (for touch screens)
	 * @param vitaTouchButton The vita button to bind
	 * @param psxTouchButton The ps one button to bind
	 * @throws Exception If the binding cannot be done
	 *
	 * It return false if you do a mistake in the mapping. The psone emu
	 * in psvita allow only some binding for retro touch (L and R 1,2,3)
	 * and some other for front touch (all but L1+R1 and L2+R2)
	 */
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
	 */
	public void assign(VitaButton vitaButton, PsxButton psxButton) {
		mButtonConfig[vitaButton.ordinal()] = psxButton;
	}

	public PsxButton get(VitaButton vitaButton) {
		return mButtonConfig[vitaButton.ordinal()];
	}

	public PsxTouchButton get(VitaTouchButton vitaTouchButton) {
		return mTouchConfig[vitaTouchButton.ordinal()];
	}

	public void setMapping(GamePadMapping controls) {
		mGamePadMapping = controls;
	}

	public void setMode(GamePadMode mode) {
		mControllerMode = mode;
	}

	public void setPort(GamePadPort port) {
		mControllerPort = port;
	}

	public GamePadMapping getMapping() {
		return mGamePadMapping;
	}

	public GamePadMode getMode() {
		return mControllerMode;
	}

	public GamePadPort getPort() {
		return mControllerPort;
	}

}
