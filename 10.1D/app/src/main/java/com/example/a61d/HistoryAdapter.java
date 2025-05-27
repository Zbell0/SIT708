package com.example.a61d;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<QuestionAnswer> items;

    public HistoryAdapter(List<QuestionAnswer> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        QuestionAnswer item = items.get(position);
        holder.questionText.setText("Q: " + item.getQuestion());

        StringBuilder optionsBuilder = new StringBuilder();
        ArrayList<String> options = item.getOptions();
        char optionChar = 'A';
        for (String option : options) {
            optionsBuilder.append(optionChar).append(". ").append(option).append("\n");
            optionChar++;
        }
        holder.optionsText.setText(optionsBuilder.toString());

        char correctLetter = item.getCorrectAnswer().charAt(0);
        int correctIndex = correctLetter - 'A';

        String correctText;
        if (correctIndex >= 0 && correctIndex < item.getOptions().size()) {
            correctText = item.getOptions().get(correctIndex);
        } else {
            correctText = "(Invalid correct answer index)";
        }
        holder.answerText.setText("✔ Correct Answer: " + correctText);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView questionText, optionsText, answerText;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            optionsText = itemView.findViewById(R.id.optionsText);
            answerText = itemView.findViewById(R.id.answerText);
        }
    }
}