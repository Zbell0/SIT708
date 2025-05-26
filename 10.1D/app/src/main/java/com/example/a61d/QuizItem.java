package com.example.a61d;

import java.io.Serializable;
import java.util.ArrayList;

public class QuizItem implements Serializable {
    private String question;
    private ArrayList<String> options;
    private String correctAnswer;

    public QuizItem(String question, ArrayList<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}