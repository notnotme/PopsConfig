package com.notnotme.psxbubblepad.model.gamepad;

/**
 * @author romain
 */
public enum PsxControllerMapping {

	DEFAULT("controls.default"),
	CUSTOM("controls.custom");

	private final String mName;

	private PsxControllerMapping(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
