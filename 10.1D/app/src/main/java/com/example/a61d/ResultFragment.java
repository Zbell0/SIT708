package com.example.a61d;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ResultFragment extends Fragment {

    private static final String ARG_QUIZ_ITEMS = "quiz_items";
    private static final String ARG_SELECTED_ANSWERS = "selected_answers";

    private ArrayList<QuizItem> quizItems;
    private ArrayList<String> selectedAnswers;

    public static ResultFragment newInstance(ArrayList<QuizItem> quizItems, ArrayList<String> selectedAnswers) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ_ITEMS, quizItems);
        args.putSerializable(ARG_SELECTED_ANSWERS, selectedAnswers);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        ListView listView = view.findViewById(R.id.resultListView);
        Button continueButton = view.findViewById(R.id.continueButton);

        if (getArguments() != null) {
            quizItems = (ArrayList<QuizItem>) getArguments().getSerializable(ARG_QUIZ_ITEMS);
            selectedAnswers = (ArrayList<String>) getArguments().getSerializable(ARG_SELECTED_ANSWERS);
        }

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < quizItems.size(); i++) {
            QuizItem item = quizItems.get(i);
            String selected = selectedAnswers.get(i);
            boolean correct = selected.contains(item.getCorrectAnswer());

            String resultText = "Q" + (i + 1) + ": " + item.getQuestion() + "\n"
                    + "Your Answer: " + selected + "\n"
                    + "Correct Answer: " + item.getCorrectAnswer() + "\n"
                    + (correct ? "✅ Correct" : "❌ Incorrect");
            results.add(resultText);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, results);
        listView.setAdapter(adapter);

        continueButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(null, 1); // 모든 프래그먼트 스택 지움
        });

        return view;
    }
}