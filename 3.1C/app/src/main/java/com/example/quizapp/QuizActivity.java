package com.example.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    TextView tvWelcome, tvQuestion, tvQuestionCount;
    RadioGroup radioGroup;
    RadioButton rb1, rb2, rb3;
    Button btnSubmit;
    ProgressBar progressBar;

    // 🧠 Android App Development Quiz Questions
    String[] questions = {
            "Which language is primarily used for Android app development?",
            "What is the file extension for an Android application package?",
            "Which layout arranges elements vertically or horizontally?",
            "What is the name of the file where you define UI components in Android?",
            "What does XML stand for in Android?"
    };

    String[][] options = {
            {"Java", "C++", "Swift"},
            {".apk", ".exe", ".dex"},
            {"RelativeLayout", "LinearLayout", "ConstraintLayout"},
            {"AndroidManifest.xml", "activity_main.xml", "build.gradle"},
            {"Extensible Markup Language", "Extra Modular Layout", "Execute Main Logic"}
    };

    int[] correctAnswers = {0, 0, 1, 1, 0}; // Correct answer index for each question

    int currentQuestion = 0;
    int score = 0;
    String userName;

    boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userName = getIntent().getStringExtra("user_name");

        tvWelcome = findViewById(R.id.tv_welcome);
        tvQuestion = findViewById(R.id.tv_question);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        radioGroup = findViewById(R.id.radioGroup);
        rb1 = findViewById(R.id.rb_option1);
        rb2 = findViewById(R.id.rb_option2);
        rb3 = findViewById(R.id.rb_option3);
        btnSubmit = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progressBar);

        tvWelcome.setText("Welcome " + userName + "!");

        loadQuestion();

        btnSubmit.setOnClickListener(v -> {
            if (!isAnswered) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(this, "Select an answer", Toast.LENGTH_SHORT).show();
                } else {
                    checkAnswer();
                    btnSubmit.setText("Next");
                    isAnswered = true;
                }
            } else {
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    loadQuestion();
                    btnSubmit.setText("Submit");
                    isAnswered = false;
                } else {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("user_name", userName);
                    intent.putExtra("score", score);
                    intent.putExtra("total", questions.length);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void loadQuestion() {
        radioGroup.clearCheck();
        resetColors();

        tvQuestion.setText(questions[currentQuestion]);
        rb1.setText(options[currentQuestion][0]);
        rb2.setText(options[currentQuestion][1]);
        rb3.setText(options[currentQuestion][2]);

        tvQuestionCount.setText((currentQuestion + 1) + "/" + questions.length);
        progressBar.setProgress(currentQuestion + 1);
    }

    private void checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadio = findViewById(selectedId);

        int selectedIndex = radioGroup.indexOfChild(selectedRadio);
        int correctIndex = correctAnswers[currentQuestion];

        if (selectedIndex == correctIndex) {
            selectedRadio.setTextColor(Color.GREEN);
            score++;
        } else {
            selectedRadio.setTextColor(Color.RED);
            RadioButton correctRadio = (RadioButton) radioGroup.getChildAt(correctIndex);
            correctRadio.setTextColor(Color.GREEN);
        }
    }

    private void resetColors() {
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
    }
}