package com.notnotme.popsconfig.model.screen;

/**
 * @author romain
 */
public enum ScreenFilter {
	
	NONE("screen.filter.none"),
	BILINEAR("screen.filter.bilinear");

	private final String mName;

	private ScreenFilter(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}