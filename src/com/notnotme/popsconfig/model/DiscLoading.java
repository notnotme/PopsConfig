package com.notnotme.popsconfig.model;

/**
 * @author romain
 */
public enum DiscLoading {
	
	NORMAL("disc.normal"),
	FAST("disc.fast");

	private final String mName;

	private DiscLoading(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
