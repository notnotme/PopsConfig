package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum VitaButton {

	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	TRIANGLE("triangle"),
	CIRCLE("circle"),
	CROSS("cross"),
	SQUARE("square"),
	L("l"),
	R("r"),
	LEFT_ANALOG_UP("analog.left.up"),
	LEFT_ANALOG_DOWN("analog.left.down"),
	LEFT_ANALOG_LEFT("analog.left.left"),
	LEFT_LANALOG_RIGHT("analog.left.right"),
	RIGHT_ANALOG_UP("analog.right.up"),
	RIGHT_ANALOG_DOWN("analog.right.down"),
	RIGHT_ANALOG_LEFT("analog.right.left"),
	RIGHT_ANALOG_RIGHT("analog.right.right");

	private final String mName;

	private VitaButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
