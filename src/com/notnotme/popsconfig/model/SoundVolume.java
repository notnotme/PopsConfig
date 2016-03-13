package com.notnotme.popsconfig.model;

/**
 * @author romain
 */
public enum SoundVolume {
	
	NORMAL("sound.vol1"),
	PLUS_1("sound.vol2"),
	PLUS_2("sound.vol3");

	private final String mName;

	private SoundVolume(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

}
