package com.iwuvhugs.seekgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button startGameButton;
    private Button showResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        startGameButton = (Button) findViewById(R.id.start_game_button);
//        showResultsButton = (Button) findViewById(R.id.show_stats_button);
        startGameButton.setOnClickListener(buttonsListener);
//        showResultsButton.setOnClickListener(buttonsListener);
    }

    private View.OnClickListener buttonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_game_button:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    break;
//                case R.id.show_stats_button:
//                    break;
//                case
            }

        }
    };
}
