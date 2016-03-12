package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum GamePadMode {

	ANALOG("mode.analog"),
	NUMERIC("mode.numeric");

	private final String mName;

	private GamePadMode(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
