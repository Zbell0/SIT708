package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.BundleCompat;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.et_name);
        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(viw->{
            String name = etName.getText().toString().trim();
            if(name.isEmpty()){
                Toast.makeText(MainActivity.this,"Please enter your name",Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(MainActivity.this,QuizActivity.class);
                intent.putExtra("user_name",name);
                startActivity(intent);
                finish();
            }
        });
    }
}