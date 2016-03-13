package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum GamePadPort {

	PORT_1("controls.port.pad1"),
	PORT_2("controls.port.pad2");

	private final String mName;

	private GamePadPort(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
