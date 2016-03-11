package com.notnotme.psxbubblepad.model.gamepad;

/**
 * @author romain
 */
public enum VitaButton {

	UP("up"),
	DOWN("down"),
	LEFT("left"),
	RIGHT("right"),
	CROSS("cross"),
	SQUARE("square"),
	CIRCLE("circle"),
	TRIANGLE("triangle"),
	L("l"),
	R("r"),
	LEFT_ANALOG_LEFT("analog.left.left"),
	LEFT_LANALOG_RIGHT("analog.left.right"),
	LEFT_ANALOG_UP("analog.left.up"),
	LEFT_ANALOG_DOWN("analog.left.down"),
	RIGHT_ANALOG_LEFT("analog.right.left"),
	RIGHT_ANALOG_RIGHT("analog.right.right"),
	RIGHT_ANALOG_UP("analog.right.up"),
	RIGHT_ANALOG_DOWN("analog.right.down"),
	TOUCH_UPPER_LEFT("touch.upper.left"),
	TOUCH_UPPER_RIGHT("touch.upper.right"),
	TOUCH_BOTTOM_LEFT("touch.bottom.left"),
	TOUCH_BOTTOM_RIGHT("touch.bottom.right"),
	REAR_TOUCH_UPPER_LEFT("touch.rear.upper.left"),
	REAR_TOUCH_UPPER_RIGHT("touch.rear.upper.right"),
	REAR_TOUCH_BOTTOM_LEFT("touch.rear.bottom.left"),
	REAR_TOUCH_BOTTOM_RIGHT("touch.rear.bottom.right");

	private final String mName;

	private VitaButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
