package com.functionmaker.airplanegame.activities;

import com.functionmaker.airplanegame.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartGameActivity extends Activity {
	private Button startGameButton;
	private Button endGameButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_game);
		startGameButton = (Button) findViewById(R.id.startGame);
		endGameButton = (Button) findViewById(R.id.endGame);
		startGameButton.setOnClickListener(new MyOnClickListener());
		endGameButton.setOnClickListener(new MyOnClickListener());
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
			case R.id.endGame:
				StartGameActivity.this.finish();
				System.exit(0);
				break;
			}
		}

	}
}
