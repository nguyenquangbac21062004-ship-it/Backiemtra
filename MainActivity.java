package com.example.backiemtra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvScore1, tvScore2, tvScore3;
    ImageView img1, img2, img3;
    Button btnPlay;

    int score1 = 0, score2 = 0, score3 = 0;
    Handler handler = new Handler();
    Random random = new Random();

    boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore1 = findViewById(R.id.tvScore1);
        tvScore2 = findViewById(R.id.tvScore2);
        tvScore3 = findViewById(R.id.tvScore3);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);

        btnPlay = findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(v -> startRace());
    }

    private void startRace() {
        if (isRunning) return; // tránh bấm nhiều lần

        resetPosition();

        isRunning = true;

        handler.postDelayed(raceRunnable, 50);
    }

    private Runnable raceRunnable = new Runnable() {
        @Override
        public void run() {

            moveAnimal(img1);
            moveAnimal(img2);
            moveAnimal(img3);

            int finishLine = 900; // khoảng cách màn hình (tùy máy có thể chỉnh)

            if (img1.getX() >= finishLine ||
                    img2.getX() >= finishLine ||
                    img3.getX() >= finishLine) {

                isRunning = false;
                checkWinner();
                return;
            }

            handler.postDelayed(this, 50);
        }
    };

    private void moveAnimal(ImageView img) {
        img.setX(img.getX() + random.nextInt(20)); // tốc độ random 0–19
    }

    private void resetPosition() {
        img1.setX(0);
        img2.setX(0);
        img3.setX(0);
    }

    private void checkWinner() {
        float p1 = img1.getX();
        float p2 = img2.getX();
        float p3 = img3.getX();

        if (p1 > p2 && p1 > p3) {
            score1 += 10;
            score2 -= 5;
            score3 -= 5;
        } else if (p2 > p1 && p2 > p3) {
            score2 += 10;
            score1 -= 5;
            score3 -= 5;
        } else {
            score3 += 10;
            score1 -= 5;
            score2 -= 5;
        }

        updateScore();
    }

    private void updateScore() {
        tvScore1.setText(String.valueOf(score1));
        tvScore2.setText(String.valueOf(score2));
        tvScore3.setText(String.valueOf(score3));
    }
}
