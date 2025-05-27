package com.example.a61d;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private TextView questionText;
    private RadioGroup optionsGroup;
    private Button nextButton;
    private LinearLayout loadingContainer;

    private ArrayList<QuizItem> quizItems = new ArrayList<>();
    private int currentIndex = 0;
    private ArrayList<String> selectedAnswers = new ArrayList<>();

    private String topic = "Movies";
    private String username;
    private static final String BASE_URL = "http://10.0.2.2:5001/getQuiz?topic=";

    public static QuizFragment newInstance(String topic, String username) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putString("topic", topic);
        args.putString("username", username);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        questionText = view.findViewById(R.id.questionText);
        optionsGroup = view.findViewById(R.id.optionsGroup);
        nextButton = view.findViewById(R.id.nextButton);
        loadingContainer = view.findViewById(R.id.loadingContainer);

        if (getArguments() != null) {
            topic = getArguments().getString("topic", "Movies");
            username = getArguments().getString("username", "anonymous");
        }

        fetchQuizData();

        nextButton.setOnClickListener(v -> {
            if (optionsGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selected = view.findViewById(optionsGroup.getCheckedRadioButtonId());
            String selectedLetter = (String) selected.getTag();
            selectedAnswers.add(selectedLetter);

            currentIndex++;
            if (currentIndex < quizItems.size()) {
                displayQuestion();
            } else {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ResultFragment.newInstance(quizItems, selectedAnswers,username))
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void fetchQuizData() {
        String url = BASE_URL + topic;

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    loadingContainer.setVisibility(View.GONE);
                    Log.d("QuizFragment", "Trying to parse quiz...");
                    try {
                        Log.d("QuizFragment", "Parsing successful.");
                        JSONArray quizArray = response.getJSONArray("quiz");
                        quizItems.clear();

                        for (int i = 0; i < quizArray.length(); i++) {
                            JSONObject obj = quizArray.getJSONObject(i);
                            String question = obj.getString("question");
                            JSONArray options = obj.getJSONArray("options");
                            String correctAnswer = obj.getString("correct_answer");

                            ArrayList<String> optionList = new ArrayList<>();
                            for (int j = 0; j < options.length(); j++) {
                                optionList.add(options.getString(j));
                            }

                            quizItems.add(new QuizItem(question, optionList, correctAnswer));
                        }

                        displayQuestion();

                    } catch (Exception e) {
                        Log.e("QuizFragment", "Error parsing quiz JSON", e);
                        Toast.makeText(requireContext(), "Something went wrong while loading the quiz.", Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    loadingContainer.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "Error fetching quiz: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        loadingContainer.setVisibility(View.VISIBLE);
        queue.add(jsonObjectRequest);
    }

    private void displayQuestion() {
        QuizItem current = quizItems.get(currentIndex);
        questionText.setText(current.getQuestion());
        optionsGroup.removeAllViews();

        String[] letters = {"A", "B", "C", "D"};

        for (int i = 0; i < current.getOptions().size(); i++) {
            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(current.getOptions().get(i));
            radioButton.setTextSize(16);
            radioButton.setTag(letters[i]);
            optionsGroup.addView(radioButton);
        }

        if (currentIndex == quizItems.size() - 1) {
            nextButton.setText("Submit");
        } else {
            nextButton.setText("Next");
        }
    }
}