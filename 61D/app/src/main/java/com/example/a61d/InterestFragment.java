package com.example.a61d;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class InterestFragment extends Fragment {

    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();
    private Button nextButton;

    public InterestFragment() {
        // Required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interest, container, false);

        LinearLayout interestsContainer = view.findViewById(R.id.interestsContainer);
        nextButton = view.findViewById(R.id.nextButton);


        String[] interests = {
                "Algorithms", "Data Structures", "Web Development", "Testing",
                "Machine Learning", "Artificial Intelligence", "Mobile Apps", "Backend Development",
                "Databases", "Cloud Computing"
        };

        for (String interest : interests) {
            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setText(interest);
            interestsContainer.addView(checkBox);
            checkBoxes.add(checkBox);
        }

        nextButton.setOnClickListener(v -> {
            ArrayList<String> selected = new ArrayList<>();
            for (CheckBox cb : checkBoxes) {
                if (cb.isChecked()) {
                    selected.add(cb.getText().toString());
                }
            }

            if (selected.isEmpty()) {
                Toast.makeText(getContext(), "Please select at least one interest", Toast.LENGTH_SHORT).show();
            } else if (selected.size() > 10) {
                Toast.makeText(getContext(), "You can select up to 10 interests only", Toast.LENGTH_SHORT).show();
            } else {

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}