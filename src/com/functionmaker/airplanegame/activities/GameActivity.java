package com.functionmaker.airplanegame.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.functionmaker.airplanegame.R;
import com.functionmaker.airplanegame.objects.Airplane;
import com.functionmaker.airplanegame.objects.Enemy;
import com.functionmaker.airplanegame.threads.GameMainThread;
import com.functionmaker.airplanegame.util.ConstValues;
import com.functionmaker.airplanegame.util.WindowSize;

public class GameActivity extends Activity {
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Bitmap airplaneBitmap;
	private Bitmap bulletBitmap;
	private int airplaneWidth;
	private int airplaneHeight;
	private int bulletWidth;
	private int bulletHeight;
	private WindowSize windowSize;
	private Airplane airplane;
	private List<Enemy> enemies;
	private GameMainThread gameMainThread;
	private Dialog exitDialog;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		init();
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameMainThread.setGamePause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			keyDownEvent(keyCode);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void init() {
		airplaneBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.airplane);
		airplaneWidth = airplaneBitmap.getWidth();
		airplaneHeight = airplaneBitmap.getHeight();
		bulletBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.bullet);
		bulletWidth = bulletBitmap.getWidth();
		bulletHeight = bulletBitmap.getHeight();
		windowSize = new WindowSize(this);
		int airplanePosX = windowSize.getWidth() / 2 - airplaneWidth / 2;
		int airplanePosY = windowSize.getHeight() - airplaneHeight;
		airplane = new Airplane(airplaneBitmap, bulletBitmap, airplaneWidth,
				airplaneHeight, bulletWidth, bulletHeight, airplanePosX,
				airplanePosY);
		surfaceView = (SurfaceView) findViewById(R.id.gameSurfaceView);
		surfaceHolder = surfaceView.getHolder();

		surfaceHolder.addCallback(new GameSurfaceCallBack());
		surfaceView.setOnTouchListener(new GameOnTouchListener());
		enemies = new ArrayList<Enemy>();
		exitDialog = new Dialog(GameActivity.this);
		exitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		exitDialog.setContentView(R.layout.exit_game_dialog);
		TextView okTextView = (TextView) exitDialog
				.findViewById(R.id.okTextView);
		TextView cancelTextView = (TextView) exitDialog
				.findViewById(R.id.cancelTextView);
		okTextView.setOnClickListener(new DialogOnClikListener());
		cancelTextView.setOnClickListener(new DialogOnClikListener());
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (msg.what == ConstValues.GAME_OVER_MSG) {
					Intent intent = new Intent(GameActivity.this,
							GameOverActivity.class);
					startActivity(intent);
					gameMainThread.setGamePause();
					gameMainThread.exitGame();
					GameActivity.this.finish();
					System.exit(0);
				}
			}

		};
	}

	private class GameSurfaceCallBack implements Callback {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			gameMainThread = new GameMainThread(holder, airplane, enemies,
					GameActivity.this, windowSize, handler);
			gameMainThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}

	}

	private class GameOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				actionDownEvent();
				break;
			case MotionEvent.ACTION_MOVE:
				actionMoveEvent(x, y);
				break;
			case MotionEvent.ACTION_UP:

				break;
			default:
				break;
			}
			return true;
		}

	}

	public void actionMoveEvent(int x, int y) {
		int tempX = x - airplaneWidth / 2;// 限制飞机不能飞出屏幕边界
		int tempY = y - airplaneHeight / 2;
		if (tempX <= 0) {
			tempX = 0;
		}
		if ((tempX + airplaneWidth) >= windowSize.getWidth()) {
			tempX = windowSize.getWidth() - airplaneWidth;
		}
		if (tempY <= 0) {
			tempY = 0;
		}
		if ((tempY + airplaneHeight) >= windowSize.getHeight()) {
			tempY = windowSize.getHeight() - airplaneHeight;
		}
		airplane.moveTo(tempX, tempY);
	}

	public void actionDownEvent() {
		if (gameMainThread.getGameState()) {
			gameMainThread.resumeGame();
		}
	}

	public void keyDownEvent(int keyCode) {
		gameMainThread.setGamePause();
		exitDialog.show();
	}

	private class DialogOnClikListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.okTextView:
				GameActivity.this.finish();
				System.exit(0);
				break;
			case R.id.cancelTextView:
				gameMainThread.resumeGame();
				exitDialog.hide();
				break;
			}
		}

	}
}
