package com.example.a61d;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private static final String ARG_USERNAME = "username";

    private String username;
    private TextView welcomeText;
    private EditText topicInput;
    private Button generateQuizButton, backBtn;

    public HomeFragment() {
        // Required empty constructor
    }

    public static HomeFragment newInstance(String username) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);



        welcomeText = view.findViewById(R.id.welcomeText);
        topicInput = view.findViewById(R.id.topicInput);
        generateQuizButton = view.findViewById(R.id.generateQuizButton);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        if (getArguments() != null) {
            username = getArguments().getString(ARG_USERNAME, "Student");
            welcomeText.setText("Hello, " + username + "!");
        }

        generateQuizButton.setOnClickListener(v -> {
            String topic = topicInput.getText().toString().trim();
            if (!topic.isEmpty()) {
                QuizFragment quizFragment = QuizFragment.newInstance(topic);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, quizFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Please enter a topic", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}