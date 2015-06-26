package com.functionmaker.airplanegame.ai;

import java.util.Iterator;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;

import com.functionmaker.airplanegame.objects.Airplane;
import com.functionmaker.airplanegame.objects.Bullet;
import com.functionmaker.airplanegame.objects.Enemy;
import com.functionmaker.airplanegame.util.ConstValues;
import com.functionmaker.airplanegame.util.WindowSize;

public class AI {
	public static int score = 0;

	public static void destroyDeal(Airplane paramAirplane,
			List<Enemy> enemiesList, Canvas paramCanvas, Paint paramPaint,
			Bitmap paramBitmap) {
		Iterator<Bullet> bulletsIterator = paramAirplane.getBullets()
				.iterator();
		while (bulletsIterator.hasNext()) {
			Bullet localBullet = bulletsIterator.next();
			if (localBullet.getY() <= 0) {
				Iterator<Enemy> enemiesIterator = enemiesList.iterator();
				while (enemiesIterator.hasNext()) {
					Enemy localEnemy = enemiesIterator.next();
					if (isHit(localBullet, localEnemy)) {
						try {
							bulletsIterator.remove();
							enemiesIterator.remove();
							paramCanvas.drawBitmap(paramBitmap,
									localBullet.getX(), localBullet.getY(),
									paramPaint);
							score += 10;

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
	}

	public static void isGameOver(List<Enemy> enemiesList,
			Airplane paramAirplane, WindowSize paramWindowSize,
			Handler paramHandler) {
		Iterator<Enemy> enemiesIterator = enemiesList.iterator();
		while (enemiesIterator.hasNext()) {
			Enemy enemy = enemiesIterator.next();
			if (enemy.getY() >= paramWindowSize.getHeight()) {
				paramHandler.sendEmptyMessage(ConstValues.GAME_OVER_MSG);
			}
		}
	}

	public static boolean isHit(Bullet paramBullet, Enemy paramEnemy) {
		int i = paramBullet.getX();
		int j = paramBullet.getY();
		int k = paramEnemy.getX();
		int m = paramEnemy.getY();
		int n = paramEnemy.getWidth();
		int i1 = paramEnemy.getHeight();
		return (i > k) && (i < k + n) && (j <= m + i1);
	}
}
