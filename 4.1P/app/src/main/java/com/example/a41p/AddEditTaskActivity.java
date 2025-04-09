package com.example.a41p;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;

public class AddEditTaskActivity extends AppCompatActivity {

    private int taskId = -1;

    private EditText editTextTitle, editTextDescription, editTextDueDate;
    private Button buttonSave;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextDueDate = findViewById(R.id.edit_text_due_date);
        buttonSave = findViewById(R.id.button_save);

        if (getIntent().hasExtra("task_id")) {
            setTitle("Edit Task");
            taskId = getIntent().getIntExtra("task_id", -1);
            editTextTitle.setText(getIntent().getStringExtra("task_title"));
            editTextDescription.setText(getIntent().getStringExtra("task_description"));
            editTextDueDate.setText(getIntent().getStringExtra("task_due_date"));
        } else {
            setTitle("Add Task");
        }

        editTextDueDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddEditTaskActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        editTextDueDate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        buttonSave.setOnClickListener(view -> saveTask());
    }

    private void saveTask() {
        String title = editTextTitle.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String dueDate = editTextDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "Title and due date are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Task task = new Task(title, description, dueDate);
        if (taskId != -1) {
            task.setId(taskId);
            taskViewModel.update(task);
            Toast.makeText(this, "Task updated", Toast.LENGTH_SHORT).show();
        } else {
            taskViewModel.insert(task);
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}