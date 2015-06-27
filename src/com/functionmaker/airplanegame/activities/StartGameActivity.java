package com.functionmaker.airplanegame.activities;

import com.functionmaker.airplanegame.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StartGameActivity extends Activity {
	private Button startGameButton;
	private Button endGameButton;
	private Button highestScoreButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.start_game);
		startGameButton = (Button) findViewById(R.id.startGame);
		highestScoreButton = (Button) findViewById(R.id.hightestScore);
		endGameButton = (Button) findViewById(R.id.endGame);
		startGameButton.setOnClickListener(new MyOnClickListener());
		endGameButton.setOnClickListener(new MyOnClickListener());
		highestScoreButton.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startGame:
				Intent intent = new Intent();
				intent.setClass(StartGameActivity.this, GameActivity.class);
				startActivity(intent);
				StartGameActivity.this.finish();
				break;
			case R.id.hightestScore:
				SharedPreferences sharedPreferences = getSharedPreferences(
						"score_record", Context.MODE_PRIVATE);
				int highestScore = sharedPreferences.getInt("highestScore", 0);
				Toast.makeText(
						StartGameActivity.this,
						StartGameActivity.this.getResources().getString(
								R.string.highestScoreIs)
								+ highestScore, Toast.LENGTH_LONG).show();
				break;
			case R.id.endGame:
				StartGameActivity.this.finish();
				System.exit(0);
				break;
			}
		}

	}
}
