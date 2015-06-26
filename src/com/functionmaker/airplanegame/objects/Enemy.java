package com.functionmaker.airplanegame.objects;

import android.graphics.Bitmap;

public class Enemy {
	private Bitmap enemyBitmap;
	private int width;
	private int height;
	private int x;
	private int y;

	public Enemy(Bitmap enemyBitmap, int width, int height, int x, int y) {
		this.enemyBitmap = enemyBitmap;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		new Thread() {
			@Override
			public void run() {
				while (Enemy.this.y<1280) {
					try {
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Enemy.this.y += 10;
				}
			}
		}.start();
	}

	public Bitmap getEnemyBitmap() {
		return enemyBitmap;
	}

	public void setEnemyBitmap(Bitmap bulletBitmap) {
		this.enemyBitmap = bulletBitmap;
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
