package com.notnotme.popsconfig.model.screen;

import com.sun.javafx.geom.Rectangle;

/**
 * @author romain
 */
public final class Screen {

	private ScreenMode mMode;
	private ScreenFilter mFilter;
	private Rectangle mCustomSize;

	public Screen() {
		mMode = ScreenMode.ORIGINAL;
		mFilter = ScreenFilter.NONE;
		mCustomSize = new Rectangle();
	}

	public ScreenMode getMode() {
		return mMode;
	}

	public void setMode(ScreenMode mMode) {
		this.mMode = mMode;
	}

	public ScreenFilter getFilter() {
		return mFilter;
	}

	public void setFilter(ScreenFilter mFilter) {
		this.mFilter = mFilter;
	}

	public Rectangle getCustomSize() {
		return mCustomSize;
	}

	public void setCustomSize(Rectangle mCustomSize) {
		this.mCustomSize = mCustomSize;
	}

}
