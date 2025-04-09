package com.example.week2simpletemperatureapp;

import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup conversionTypeGroup;
    private Spinner sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueInput;
    private TextView resultText;
    private Button convertButton;

    private String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile", "Centimeter", "Meter", "Kilometer"};
    private String[] weightUnits = {"Pound", "Ounce", "Ton", "Gram", "Kilogram"};
    private String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI elements binding
        conversionTypeGroup = findViewById(R.id.conversionTypeGroup);
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destinationUnitSpinner = findViewById(R.id.destinationUnitSpinner);
        valueInput = findViewById(R.id.valueInput);
        resultText = findViewById(R.id.resultText);
        convertButton = findViewById(R.id.convertButton);

        // Default set to Length
        updateUnitSpinners(lengthUnits);

        // Update unit spinners when radio button selection changes
        conversionTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.lengthRadioButton) {
                updateUnitSpinners(lengthUnits);
            } else if (checkedId == R.id.weightRadioButton) {
                updateUnitSpinners(weightUnits);
            } else if (checkedId == R.id.temperatureRadioButton) {
                updateUnitSpinners(temperatureUnits);
            }
        });

        // Conversion button click event
        convertButton.setOnClickListener(v -> performConversion());
    }

    private void updateUnitSpinners(String[] units) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        destinationUnitSpinner.setAdapter(adapter);
    }

    private void  performConversion(){
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
        String inputText = valueInput.getText().toString();

        if(inputText.isEmpty()){
            Toast.makeText(this,"Please enter a value",Toast.LENGTH_SHORT).show();
            return;
        }

        double inputValue = 0;
        try {
            inputValue = Double.parseDouble(inputText);
        }catch (NumberFormatException e){
            Toast.makeText(this,"please enter a valid number",Toast.LENGTH_SHORT).show();
            return;
        }

        if(sourceUnit.equals(destinationUnit)){
            Toast.makeText(this, "Source and destination units cannot be the same", Toast.LENGTH_SHORT).show();
        }

        double convertedValue = 0;

        if(conversionTypeGroup.getCheckedRadioButtonId() == R.id.lengthRadioButton){
            convertedValue = convertLength(inputValue,sourceUnit,destinationUnit);
        } else if (conversionTypeGroup.getCheckedRadioButtonId() ==R.id.weightRadioButton) {
            convertedValue = convertWeight(inputValue,sourceUnit,destinationUnit);
        } else if (conversionTypeGroup.getCheckedRadioButtonId() == R.id.temperatureRadioButton) {
            convertedValue = convertTemperature(inputValue,sourceUnit,destinationUnit);
        }

        resultText.setText(String.format("Converted Value:%.2f %s",convertedValue,destinationUnit));
    }

    private double convertLength(double value, String from, String to) {
        // Convert to cm as the base unit
        double cmValue;
        switch (from) {
            case "Inch": cmValue = value * 2.54; break;
            case "Foot": cmValue = value * 30.48; break;
            case "Yard": cmValue = value * 91.44; break;
            case "Mile": cmValue = value * 160934; break;
            case "Centimeter": cmValue = value; break;
            case "Meter": cmValue = value * 100; break;
            case "Kilometer": cmValue = value * 100000; break;
            default: return value;
        }

        // Convert to the target unit
        switch (to) {
            case "Inch": return cmValue / 2.54;
            case "Foot": return cmValue / 30.48;
            case "Yard": return cmValue / 91.44;
            case "Mile": return cmValue / 160934;
            case "Centimeter": return cmValue;
            case "Meter": return cmValue / 100;
            case "Kilometer": return cmValue / 100000;
            default: return cmValue;
        }
    }

    private double convertWeight(double value, String from, String to) {
        // Convert to kg as the base unit
        double kgValue;
        switch (from) {
            case "Pound": kgValue = value * 0.453592; break;
            case "Ounce": kgValue = value * 0.0283495; break;
            case "Ton": kgValue = value * 907.185; break;
            case "Gram": kgValue = value / 1000; break;
            case "Kilogram": kgValue = value; break;
            default: return value;
        }

        // Convert to the target unit
        switch (to) {
            case "Pound": return kgValue / 0.453592;
            case "Ounce": return kgValue / 0.0283495;
            case "Ton": return kgValue / 907.185;
            case "Gram": return kgValue * 1000;
            case "Kilogram": return kgValue;
            default: return kgValue;
        }
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals("Celsius")) {
            if (to.equals("Fahrenheit")) return (value * 1.8) + 32;
            if (to.equals("Kelvin")) return value + 273.15;
        } else if (from.equals("Fahrenheit")) {
            if (to.equals("Celsius")) return (value - 32) / 1.8;
            if (to.equals("Kelvin")) return ((value - 32) / 1.8) + 273.15;
        } else if (from.equals("Kelvin")) {
            if (to.equals("Celsius")) return value - 273.15;
            if (to.equals("Fahrenheit")) return ((value - 273.15) * 1.8) + 32;
        }
        return value; // No conversion needed for the same units
    }
}