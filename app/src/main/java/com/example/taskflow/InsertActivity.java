package com.example.taskflow;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InsertActivity extends AppCompatActivity {

    AppDatabase AppDB;
    EditText titleEditText, descriptionEditText, dateEditText, timeEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent prev = getIntent();
        String username = prev.getStringExtra("username");
        String user_id = prev.getStringExtra("user_id");

        AppDB = AppDatabase.getInstance(this);
        titleEditText = findViewById(R.id.title_form);
        descriptionEditText = findViewById(R.id.description_form);
        dateEditText = findViewById(R.id.date_form);
        timeEditText = findViewById(R.id.time_form);
        saveButton = findViewById(R.id.save_button);

        // Open Date Picker when clicking date field
        dateEditText.setOnClickListener(v -> showDatePicker());

        // Open Time Picker when clicking time field
        timeEditText.setOnClickListener(v -> showTimePicker());

        saveButton.setOnClickListener(v -> saveTask(user_id,username));
    }

    private void saveTask(String user_id,String username) {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String time = timeEditText.getText().toString().trim();
        String status = "Pending";

        if (title.isEmpty() || description.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = Integer.parseInt(user_id);

        Task newTask = new Task(title, description, status, date, time, userId);

        new Thread(() -> {
            try {
                AppDB.taskDao().insert(newTask);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InsertActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                });
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();

    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .build();

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dateEditText.setText(sdf.format(selection));
        });
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this,
                (TimePicker view, int selectedHour, int selectedMinute) -> timeEditText.setText(String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)), hour, minute, true);

        timePicker.show();
    }
}
