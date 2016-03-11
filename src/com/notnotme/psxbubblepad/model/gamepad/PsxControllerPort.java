package com.notnotme.psxbubblepad.model.gamepad;

/**
 * @author romain
 */
public enum PsxControllerPort {

	PORT_1("port.pad1"),
	PORT_2("port.pad2");

	private final String mName;

	private PsxControllerPort(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
