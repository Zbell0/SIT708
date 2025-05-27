package com.example.a61d;

import java.util.ArrayList;

public class QuestionAnswer {
    private String question;
    private ArrayList<String> options;
    private String correctAnswer;

    public QuestionAnswer(String question, ArrayList<String> options, String correctAnswer) {
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