package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum GamePadMode {

	NUMERIC("controls.mode.numeric"),
	ANALOG("controls.mode.analog");

	private final String mName;

	private GamePadMode(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
