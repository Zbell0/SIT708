package com.example.a71p;

import static com.example.a71p.R.id.goBackBtn;
import static com.example.a71p.R.id.radioGroup;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a71p.database.LostFoundDatabaseHelper;
import com.example.a71p.model.LostFoundItem;

import java.util.Calendar;

public class CreateAdvertFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioLost, radioFound;
    private EditText editName, editPhone, editDescription, editDate, editLocation;
    private Button btnSave;

    public CreateAdvertFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_advert, container, false);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioLost = view.findViewById(R.id.radioLost);
        radioFound = view.findViewById(R.id.radioFound);
        editName = view.findViewById(R.id.editName);
        editPhone = view.findViewById(R.id.editPhone);
        editDescription = view.findViewById(R.id.editDescription);
        editDate = view.findViewById(R.id.editDate);
        editLocation = view.findViewById(R.id.editLocation);
        btnSave = view.findViewById(R.id.btnSave);

        editPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editDate.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        btnSave.setOnClickListener(v -> {
            String type = radioLost.isChecked() ? "Lost" : "Found";
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String description = editDescription.getText().toString().trim();
            String date = editDate.getText().toString().trim();
            String location = editLocation.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(description)
                    || TextUtils.isEmpty(date) || TextUtils.isEmpty(location)) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }


            LostFoundDatabaseHelper dbHelper = new LostFoundDatabaseHelper(requireContext());
            LostFoundItem item = new LostFoundItem(type, name, phone, description, date, location);
            dbHelper.insertItem(item);

            Toast.makeText(getContext(), type + " item saved!", Toast.LENGTH_SHORT).show();


            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ItemsListFragment())
                    .addToBackStack(null)
                    .commit();
        });
        Button goBackBtn = view.findViewById(R.id.goBackBtn);

        goBackBtn.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}