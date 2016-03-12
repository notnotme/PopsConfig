package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum GamePadMapping {

	DEFAULT("controls.default"),
	CUSTOM("controls.custom");

	private final String mName;

	private GamePadMapping(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
