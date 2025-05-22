package com.example.a71p;

import static com.example.a71p.R.id.radioGroup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateAdvertFragment extends Fragment {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1001;

    private RadioGroup radioGroup;
    private RadioButton radioLost, radioFound;
    private EditText editName, editPhone, editDescription, editDate, editLocation;
    private Button btnSave;

    public CreateAdvertFragment() {}

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


        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), "AIzaSyCsvH8JPiDmhuchwI8vpMGau_7q_dg9ei8", Locale.getDefault());
        }


        editLocation.setFocusable(false);
        editLocation.setOnClickListener(v -> {
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(requireContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });


        editDate.setFocusable(false);
        editDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                    (view1, y, m, d) -> editDate.setText(y + "-" + (m + 1) + "-" + d),
                    year, month, day);
            dialog.show();
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

            LostFoundItem item = new LostFoundItem(type, name, phone, description, date, location);
            new LostFoundDatabaseHelper(requireContext()).insertItem(item);

            Toast.makeText(getContext(), type + " item saved!", Toast.LENGTH_SHORT).show();

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ItemsListFragment())
                    .addToBackStack(null)
                    .commit();
        });


        Button goBackBtn = view.findViewById(R.id.goBackBtn);
        goBackBtn.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (place.getLatLng() != null) {
                    double lat = place.getLatLng().latitude;
                    double lng = place.getLatLng().longitude;
                    Log.d("CreateAdvertFragment", "Selected location: " + lat + "," + lng);
                    editLocation.setText(lat + "," + lng);
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR && data != null) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.e("CreateAdvertFragment", "Error: " + status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("CreateAdvertFragment", "Location selection cancelled.");
            }
        }
    }
}