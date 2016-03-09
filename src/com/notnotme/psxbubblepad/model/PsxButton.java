package com.notnotme.psxbubblepad.model;

/**
 * @author romain
 */
public enum PsxButton {

	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	START("start"),
	SELECT("select"),
	CROSS("cross"),
	SQUARE("square"),
	CIRCLE("circle"),
	TRIANGLE("triangle"),
	L1("l1"),
	L2("l2"),
	L3("l3"),
	R1("r1"),
	R2("r2"),
	R3("r3"),
	L1_R1("l1_r1"),
	L2_R2("l2_r2"),
	UNUSED("unused");

	private final String mName;

	private PsxButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
