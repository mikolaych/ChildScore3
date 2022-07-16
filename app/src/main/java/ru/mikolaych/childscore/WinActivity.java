package ru.mikolaych.childscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {
    private MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        playMusic();

    }
    public void playMusic() {
        backgroundMusic = MediaPlayer.create(this, R.raw.victory);
            backgroundMusic.start();

    }

    public void newGame(View view) {
        Button NewGame = findViewById(R.id.newGameButton);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        backgroundMusic.stop();
    }

}