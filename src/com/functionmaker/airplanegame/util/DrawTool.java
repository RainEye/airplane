package com.functionmaker.airplanegame.util;

import java.util.Iterator;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.functionmaker.airplanegame.objects.Airplane;
import com.functionmaker.airplanegame.objects.Bullet;
import com.functionmaker.airplanegame.objects.Enemy;

public class DrawTool {
	public static void drawAirplane(Canvas paramCanvas, Paint paramPaint,
			Airplane paramAirplane) {
		paramCanvas.drawBitmap(paramAirplane.getAirplaneBitmap(),
				paramAirplane.getX(), paramAirplane.getY(), paramPaint);
	}

	public static void drawBullet(Canvas paramCanvas, Paint paramPaint,
			Airplane paramAirplane) {
		Iterator<Bullet> bulletsIterator = paramAirplane.getBullets()
				.iterator();
		while (bulletsIterator.hasNext()) {
			Bullet localBullet = bulletsIterator.next();
			if (localBullet.getY() > 0) {
				paramCanvas.drawBitmap(localBullet.getBulletBitmap(),
						localBullet.getX(), localBullet.getY(), paramPaint);
			} else {
				bulletsIterator.remove();
			}
		}
	}

	public static void drawEnemies(Canvas paramCanvas, Paint paramPaint,
			List<Enemy> paramEnemies, WindowSize paramWindowSize) {
		Iterator<Enemy> enemiesIterator = paramEnemies.iterator();
		while (enemiesIterator.hasNext()) {
			Enemy localEnemy = enemiesIterator.next();
			if (localEnemy.getY() < paramWindowSize.getHeight()) {
				paramCanvas.drawBitmap(localEnemy.getEnemyBitmap(),
						localEnemy.getX(), localEnemy.getY(), paramPaint);
			}
		}
	}
}
