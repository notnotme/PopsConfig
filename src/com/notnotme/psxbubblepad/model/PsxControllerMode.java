package com.notnotme.psxbubblepad.model;

/**
 * @author romain
 */
public enum PsxControllerMode {

	ANALOG("mode.analog"),
	NUMERIC("mode.numeric");

	private final String mName;

	private PsxControllerMode(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
