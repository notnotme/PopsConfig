package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum PsxButton {

	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	TRIANGLE("triangle"),
	CIRCLE("circle"),
	CROSS("cross"),
	SQUARE("square"),
	L1("l1"),
	R1("r1"),
	L2("l2"),
	R2("r2"),
	L3("l3"),
	R3("r3"),
	SELECT("select"),
	START("start"),
	UNUSED("unused"),
	L1_R1("l1_r1"),
	L2_R2("l2_r2");

	private final String mName;

	private PsxButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
