package com.functionmaker.airplanegame.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.functionmaker.airplanegame.R;

public class GameOverActivity extends Activity {
	private Button restartGameButton;
	private Button endGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game_over_activity);
		restartGameButton = (Button) findViewById(R.id.restartGameButton);
		endGameButton = (Button) findViewById(R.id.endGameButton);
		restartGameButton.setOnClickListener(new MyOnClickListener());
		endGameButton.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.restartGameButton:
				Intent intent = new Intent(GameOverActivity.this,
						GameActivity.class);
				startActivity(intent);
				break;
			case R.id.endGameButton:
				GameOverActivity.this.finish();
				System.exit(0);
			}
		}
	}
}
