package com.functionmaker.airplanegame.ai;

import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
			if (localBullet.getY() > 0) {
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
			Handler paramHandler, Context context) {
		Iterator<Enemy> enemiesIterator = enemiesList.iterator();
		while (enemiesIterator.hasNext()) {
			Enemy enemy = enemiesIterator.next();
			if (enemy.getY() >= paramWindowSize.getHeight()) {
				savaHighestScore(context);
				paramHandler.sendEmptyMessage(ConstValues.GAME_OVER_MSG);
			}
		}
	}

	public static boolean isHit(Bullet paramBullet, Enemy paramEnemy) {
		int bulletX = paramBullet.getX();
		int bulletY = paramBullet.getY();
		int enemyX = paramEnemy.getX();
		int enemyY = paramEnemy.getY();
		int enemyWidth = paramEnemy.getWidth();
		int enemyHeight = paramEnemy.getHeight();
		if (bulletX >= enemyX && bulletX <= enemyX + enemyWidth
				&& (bulletY <= enemyY + enemyHeight)) {
			return true;
		} else {
			return false;
		}
	}

	public static void savaHighestScore(Context context) {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"score_record", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		int highestSocre = sharedPreferences.getInt("highestScore", 0);
		if (score > highestSocre) {
			editor.putInt("highestScore", score);
			editor.commit();
		}
	}
}
