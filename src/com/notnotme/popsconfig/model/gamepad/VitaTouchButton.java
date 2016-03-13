package com.notnotme.popsconfig.model.gamepad;

/**
 * @author romain
 */
public enum VitaTouchButton {
	
	TOUCH_UPPER_LEFT("touch.upper.left"),
	TOUCH_UPPER_RIGHT("touch.upper.right"),
	TOUCH_BOTTOM_LEFT("touch.bottom.left"),
	TOUCH_BOTTOM_RIGHT("touch.bottom.right"),
	REAR_TOUCH_UPPER_LEFT("touch.rear.upper.left"),
	REAR_TOUCH_UPPER_RIGHT("touch.rear.upper.right"),
	REAR_TOUCH_BOTTOM_LEFT("touch.rear.bottom.left"),
	REAR_TOUCH_BOTTOM_RIGHT("touch.rear.bottom.right");

	private final String mName;

	private VitaTouchButton(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
