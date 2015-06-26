package com.functionmaker.airplanegame.objects;

import android.graphics.Bitmap;

import com.functionmaker.airplanegame.util.ConstValues;

public class Bullet {// ×Óµ¯Àà
	private Bitmap bulletBitmap;
	private int width;
	private int height;
	private int x;
	private int y;

	public Bullet(Bitmap bulletBitmap, int width, int height, int x, int y) {
		this.bulletBitmap = bulletBitmap;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		new Thread() {
			@Override
			public void run() {
				while (Bullet.this.y > 0) {
					try {
						Thread.sleep(500);
						Bullet.this.y -= ConstValues.BULLET_OFFSET;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	public Bitmap getBulletBitmap() {
		return bulletBitmap;
	}

	public void setBulletBitmap(Bitmap bulletBitmap) {
		this.bulletBitmap = bulletBitmap;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
