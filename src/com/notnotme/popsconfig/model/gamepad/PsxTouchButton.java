package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum PsxTouchButton {

	UNUSED("unused"),
	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	TRIANGLE("triangle"),
	CIRCLE("circle"),
	CROSS("cross"),
	SQUARE("square"),
	START("start"),
	SELECT("select"),
	L1("l1"),
	R1("r1"),
	L2("l2"),
	R2("r2"),
	L3("l3"),
	R3("r3");

	private final String mName;

	private PsxTouchButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
