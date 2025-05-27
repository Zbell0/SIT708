package com.example.a61d;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private Button backBtn;
    private static final String FETCH_HISTORY_URL = "http://10.0.2.2:5001/history";
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private ArrayList<QuestionAnswer> itemList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.historyRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new HistoryAdapter(itemList);
        recyclerView.setAdapter(adapter);

        fetchHistoryData();

        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());



        return view;

    }


    private void fetchHistoryData() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());


        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                FETCH_HISTORY_URL,
                response -> {
                    Log.d("RAW_RESPONSE", "Raw server response: " + response);
                    parseHistoryResponse(response);
                },
                error -> {
                    Log.e("HISTORY_ERROR", "Failed to fetch history");
                    if (error.networkResponse != null) {
                        Log.e("HISTORY_ERROR", "Status: " + error.networkResponse.statusCode);
                        try {
                            String errorResponse = new String(error.networkResponse.data, "UTF-8");
                            Log.e("HISTORY_ERROR", "Error response: " + errorResponse);
                        } catch (Exception e) {
                            Log.e("HISTORY_ERROR", "Could not parse error response");
                        }
                    }
                    error.printStackTrace();
                }
        );

        queue.add(stringRequest);
    }

    private void parseHistoryResponse(String responseString) {
        try {
            if (responseString == null || responseString.trim().isEmpty()) {
                Log.e("HISTORY_ERROR", "Empty response from server");
                return;
            }

            JSONObject response = new JSONObject(responseString);

            if (!response.has("history")) {
                Log.e("HISTORY_ERROR", "No 'history' field in response");
                return;
            }

            Object historyObj = response.get("history");
            if (historyObj == JSONObject.NULL) {
                Log.e("HISTORY_ERROR", "History field is null");
                return;
            }

            JSONArray historyArray = response.getJSONArray("history");
            itemList.clear();

            for (int i = 0; i < historyArray.length(); i++) {
                try {
                    JSONObject item = historyArray.getJSONObject(i);
                    QuestionAnswer qa = parseQuestionAnswer(item, i);
                    if (qa != null) {
                        itemList.add(qa);
                    }
                } catch (Exception e) {
                    Log.e("HISTORY_ERROR", "Failed to parse item " + i + ": " + e.getMessage());
                }
            }

            Log.d("HISTORY_SUCCESS", "Parsed " + itemList.size() + " history items");

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

        } catch (Exception e) {
            Log.e("HISTORY_ERROR", "Failed to parse response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private QuestionAnswer parseQuestionAnswer(JSONObject obj, int index) {
        try {
            String question = obj.optString("question", "");
            String correctAnswer = obj.optString("correct_answer", "");

            if (question.isEmpty() || correctAnswer.isEmpty()) {
                Log.w("HISTORY_PARSE", "Item " + index + " has empty question or answer");
                return null;
            }

            JSONArray optionsArray = obj.optJSONArray("options");
            if (optionsArray == null || optionsArray.length() == 0) {
                Log.w("HISTORY_PARSE", "Item " + index + " has no options");
                return null;
            }

            ArrayList<String> options = new ArrayList<>();
            for (int j = 0; j < optionsArray.length(); j++) {
                String option = optionsArray.optString(j, "");
                if (!option.isEmpty()) {
                    options.add(option);
                }
            }

            if (options.isEmpty()) {
                Log.w("HISTORY_PARSE", "Item " + index + " has no valid options");
                return null;
            }

            return new QuestionAnswer(question, options, correctAnswer);

        } catch (Exception e) {
            Log.e("HISTORY_PARSE", "Error parsing item " + index + ": " + e.getMessage());
            return null;
        }
    }
}