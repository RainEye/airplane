package com.functionmaker.airplanegame.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class WindowSize {
	private int width;
	private int height;

	public WindowSize(Activity activity) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		this.width = displayMetrics.widthPixels;
		this.height = displayMetrics.heightPixels;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
