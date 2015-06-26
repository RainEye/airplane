package com.functionmaker.airplanegame.objects;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

public class Airplane {// ·É»úÀà
	private Bitmap airplaneBitmap;
	private Bitmap bulletBitmap;
	private int width;
	private int height;
	private int bulletWidth;
	private int bulletHeight;
	private int x;
	private int y;
	public boolean isExist;
	private List<Bullet> bullets;

	public Airplane(Bitmap airplaneBitmap, Bitmap bulletBitmap, int width,
			int height, int bulletWidth, int bulletHeight, int x, int y) {
		this.airplaneBitmap = airplaneBitmap;
		this.bulletBitmap = bulletBitmap;
		this.width = width;
		this.height = height;
		this.bulletWidth = bulletWidth;
		this.bulletHeight = bulletHeight;
		this.x = x;
		this.y = y;
		this.isExist = true;
		this.bullets = new ArrayList<Bullet>();
	}

	public Bitmap getAirplaneBitmap() {
		return airplaneBitmap;
	}

	public void setAirplaneBitmap(Bitmap bulletBitmap) {
		this.airplaneBitmap = bulletBitmap;
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

	public List<Bullet> getBullets() {
		return this.bullets;
	}
	public Bullet produceBullet(){
		Bullet bullet = new Bullet(Airplane.this.bulletBitmap,
				Airplane.this.width, Airplane.this.height,
				Airplane.this.x + Airplane.this.width / 2
						- Airplane.this.bulletWidth / 2,
				Airplane.this.y - Airplane.this.bulletHeight);
		Airplane.this.bullets.add(bullet);
		return bullet;
	}
}
