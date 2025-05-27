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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ResultFragment extends Fragment {

    private static final String ARG_QUIZ_ITEMS = "quiz_items";
    private static final String ARG_SELECTED_ANSWERS = "selected_answers";
    private static final String SAVE_HISTORY_URL = "http://10.0.2.2:5001/saveHistory";
    private static final String ARG_USERNAME = "username";
    private String username;

    private ArrayList<QuizItem> quizItems;
    private ArrayList<String> selectedAnswers;

    public static ResultFragment newInstance(ArrayList<QuizItem> quizItems, ArrayList<String> selectedAnswers,String username) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUIZ_ITEMS, quizItems);
        args.putSerializable(ARG_SELECTED_ANSWERS, selectedAnswers);
        args.putString(ARG_USERNAME, username);
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
        Button historyButton  = view.findViewById(R.id.historyButton);
        Button homeButton = view.findViewById(R.id.homeButton);

        username = getArguments().getString(ARG_USERNAME, "anonymous");

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
        sendHistoryToServer();
        continueButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance(username))
                    .commit();
        });
        historyButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HistoryFragment())
                    .addToBackStack(null)
                    .commit();
        });
        homeButton.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment().newInstance(username))
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }

    private void sendHistoryToServer() {
        try {
            JSONArray historyArray = new JSONArray();
            for (int i = 0; i < quizItems.size(); i++) {
                QuizItem item = quizItems.get(i);
                JSONObject obj = new JSONObject();
                obj.put("question", item.getQuestion());


                JSONArray optionsArray = new JSONArray();
                for (String option : item.getOptions()) {
                    optionsArray.put(option);
                }
                obj.put("options", optionsArray);

                obj.put("correct_answer", item.getCorrectAnswer());
                obj.put("selected_answer", selectedAnswers.get(i));
                historyArray.put(obj);
            }

            JSONObject payload = new JSONObject();
            payload.put("topic", "general");
            payload.put("history", historyArray);
            payload.put("username", username);

            RequestQueue queue = Volley.newRequestQueue(requireContext());
            JsonObjectRequest req = new JsonObjectRequest(
                    Request.Method.POST,
                    SAVE_HISTORY_URL,
                    payload,
                    response -> { /* success */ },
                    error -> error.printStackTrace()
            );
            queue.add(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}