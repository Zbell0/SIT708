package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView tvCongrats, tvScore;
    Button btnRestart, btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvCongrats = findViewById(R.id.tv_congrats);
        tvScore = findViewById(R.id.tv_score);
        btnRestart = findViewById(R.id.btn_restart);
        btnFinish = findViewById(R.id.btn_finish);

        Intent intent = getIntent();
        String name = intent.getStringExtra("user_name");
        int score = intent.getIntExtra("score", 0);
        int total = intent.getIntExtra("total", 5);

        tvCongrats.setText("Congratulations " + name + "!");
        tvScore.setText("YOUR SCORE: " + score + "/" + total);

        btnRestart.setOnClickListener(v -> {
            Intent restartIntent = new Intent(ResultActivity.this, QuizActivity.class);
            restartIntent.putExtra("user_name", name);
            startActivity(restartIntent);
            finish();
        });

        btnFinish.setOnClickListener(view->{
            Intent calculator = new Intent(ResultActivity.this, Calculator.class);
            startActivity(calculator);
            finish();
        });
    }
}