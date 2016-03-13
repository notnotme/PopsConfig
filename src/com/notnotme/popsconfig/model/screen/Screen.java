package com.notnotme.popsconfig.model.screen;

/**
 * @author romain
 */
public final class Screen {

	private ScreenMode mMode;
	private ScreenFilter mFilter;

	private int mX;
	private int mY;
	private int mWidth;
	private int mHeight;

	public Screen() {
		mMode = ScreenMode.ORIGINAL;
		mFilter = ScreenFilter.NONE;
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

	public int getX() {
		return mX;
	}

	public void setX(int mX) {
		this.mX = mX;
	}

	public int getY() {
		return mY;
	}

	public void setY(int mY) {
		this.mY = mY;
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int mHeight) {
		this.mHeight = mHeight;
	}

}
