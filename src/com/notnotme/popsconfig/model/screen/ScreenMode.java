package com.notnotme.popsconfig.model.screen;

/**
 * @author romain
 */
public enum ScreenMode {
	
	ORIGINAL("screen.mode.original"),
	NORMAL("screen.mode.normal"),
	ZOOM("screen.mode.zoom"),
	FIT("screen.mode.fit"),
	CUSTOM("screen.mode.custom");

	private final String mName;

	private ScreenMode(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
