package com.functionmaker.airplanegame.threads;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.SurfaceHolder;

import com.functionmaker.airplanegame.R;
import com.functionmaker.airplanegame.ai.AI;
import com.functionmaker.airplanegame.objects.Airplane;
import com.functionmaker.airplanegame.objects.Enemy;
import com.functionmaker.airplanegame.util.ConstValues;
import com.functionmaker.airplanegame.util.DrawTool;
import com.functionmaker.airplanegame.util.WindowSize;

public class GameMainThread extends Thread {
	private Airplane airplane;
	private Context context;
	private List<Enemy> enemies;
	private Bitmap[] enemyBitmaps;
	private int enemyHeight;
	private int enemyWidth;
	private Bitmap explosionBitmap;
	private Handler handler;
	private SurfaceHolder holder;
	private boolean isGameContinue;
	private boolean isGamePause;
	private WindowSize windowSize;
	private int enemyProducePeriod;
	private MediaPlayer bgmMediaPlayer;
	private MediaPlayer explosionMediaPlayer;

	public GameMainThread(SurfaceHolder paramSurfaceHolder,
			Airplane paramAirplane, List<Enemy> paramList,
			Context paramContext, WindowSize paramWindowSize,
			Handler paramHandler) {
		this.holder = paramSurfaceHolder;
		this.airplane = paramAirplane;
		this.enemies = paramList;
		this.windowSize = paramWindowSize;
		this.context = paramContext;
		this.handler = paramHandler;
		this.enemyBitmaps = new Bitmap[ConstValues.NUM_OF_ENEMIES];
		this.enemyProducePeriod = 60;
		this.bgmMediaPlayer = MediaPlayer.create(paramContext, R.raw.bgm);
		this.explosionMediaPlayer = MediaPlayer.create(context,
				R.raw.explosion_sound);
		try {
			bgmMediaPlayer.setLooping(true);
			bgmMediaPlayer.prepare();
			explosionMediaPlayer.setLooping(false);
			explosionMediaPlayer.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.explosionBitmap = BitmapFactory.decodeResource(
				paramContext.getResources(), R.drawable.explosion);
		this.enemyBitmaps[0] = BitmapFactory.decodeResource(
				paramContext.getResources(), R.drawable.enemy_a);
		this.enemyBitmaps[1] = BitmapFactory.decodeResource(
				paramContext.getResources(), R.drawable.enemy_b);
		this.enemyBitmaps[2] = BitmapFactory.decodeResource(
				paramContext.getResources(), R.drawable.enemy_c);
		this.enemyBitmaps[3] = BitmapFactory.decodeResource(
				paramContext.getResources(), R.drawable.enemy_d);
		this.enemyWidth = this.enemyBitmaps[0].getWidth();
		this.enemyHeight = this.enemyBitmaps[0].getHeight();
		this.isGameContinue = true;
	}

	public void run() {
		bgmMediaPlayer.start();
		Paint localPaint = new Paint();
		localPaint.setAntiAlias(true);
		localPaint.setTextSize(20.0F);
		localPaint.setColor(Color.YELLOW);
		int bulletsCount = 0;
		int enemyCount = 0;
		while (isGameContinue) {
			while (!isGamePause) {
				if (bulletsCount == 30) {
					this.airplane.produceBullet();
					bulletsCount = 0;
				}
				bulletsCount++;
				if (enemyCount == enemyProducePeriod) {
					produceEnemies();
					enemyCount = 0;
				}
				enemyCount++;
				Canvas localCanvas = this.holder.lockCanvas();
				localCanvas.drawColor(Color.BLACK);
				DrawTool.drawAirplane(localCanvas, localPaint, this.airplane);
				DrawTool.drawEnemies(localCanvas, localPaint, this.enemies,
						this.windowSize);
				DrawTool.drawBullet(localCanvas, localPaint, this.airplane);
				AI.destroyDeal(this.airplane, this.enemies, localCanvas,
						localPaint, this.explosionBitmap,
						this.explosionMediaPlayer);
				switch (AI.score) {
				case 1000:
					AI.level = 2;
					enemyProducePeriod = 30;
					break;
				case 2000:
					AI.level = 3;
					enemyProducePeriod = 15;
					break;
				case 3000:
					AI.level = 4;
					enemyProducePeriod = 10;
					break;
				default:
					break;
				}
				localCanvas.drawText(
						context.getResources().getString(R.string.score)
								+ AI.score, 20.0F, 20.0F, localPaint);
				localCanvas.drawText(
						context.getResources().getString(R.string.level)
								+ AI.level, 20.0F, 50.0F, localPaint);
				AI.isGameOver(this.enemies, this.airplane, this.windowSize,
						this.handler, context);
				this.holder.unlockCanvasAndPost(localCanvas);
			}
		}
	}

	public void setGamePause() {
		this.isGamePause = true;
		bgmMediaPlayer.pause();
	}

	public void exitGame() {
		this.isGameContinue = false;
		bgmMediaPlayer.stop();
	}

	public boolean getGameState() {
		return this.isGamePause;
	}

	public void produceEnemies() {
		int chooseEnemy = new Random().nextInt(ConstValues.NUM_OF_ENEMIES - 1);
		Enemy localEnemy = new Enemy(this.enemyBitmaps[chooseEnemy],
				this.enemyWidth, this.enemyHeight,
				new Random().nextInt(this.windowSize.getWidth()
						- this.enemyWidth), 0);
		this.enemies.add(localEnemy);
	}

	public void resumeGame() {
		this.isGamePause = false;
		bgmMediaPlayer.start();
	}
}
