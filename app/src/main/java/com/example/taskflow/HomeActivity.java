package com.example.taskflow;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class HomeActivity extends AppCompatActivity {

    AppDatabase AppDB;
    Button LogoutButton;
    Button InsertButton;

    TextView WelcomeText;
    CheckBox checkBoxPending;
    CheckBox checkBoxCompleted;
    List<Task> tasksList;
    int user_id;

    RecyclerView recyclerView;
    TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finishAffinity();
            }
        });

        AppDB = AppDatabase.getInstance(this);
        WelcomeText = findViewById(R.id.welcome);
        LogoutButton = findViewById(R.id.logout_button);
        InsertButton = findViewById(R.id.insert_button);
        checkBoxPending = findViewById(R.id.checkBoxP);
        checkBoxCompleted = findViewById(R.id.checkBoxC);



        Intent prev = getIntent();
        String username = prev.getStringExtra("username");
        String welcome_msg = "Welcome, "+username+"!";
        WelcomeText.setText(welcome_msg);

        LogoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        });

        new Thread(() -> {
            user_id = AppDB.userDao().getUserId(username);
            runOnUiThread(() -> setupInsertButton(user_id, username));
        }).start();

        recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Thread(() -> {
            user_id = AppDB.userDao().getUserId(username);
            runOnUiThread(() -> {

                setupInsertButton(user_id, username);
                loadTasks(true);
            });
        }).start();

        checkBoxPending.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxCompleted.setChecked(false);
                loadTasks(true);
            }
        });

        checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxPending.setChecked(false);
                loadTasks(false);
            }
        });

    }

    private void loadTasks(boolean isPending) {
        new Thread(() -> {
            AppDB.taskDao().updatePendingTasksToCompleted(); // 🔥 Add this line to auto-update overdue tasks
            List<Task> tasks;
            if (isPending) {
                tasks = AppDB.taskDao().getTasksByUserPending(user_id);
            } else {
                tasks = AppDB.taskDao().getTasksByUserCompleted(user_id);
            }

            runOnUiThread(() -> {
                taskAdapter = new TaskAdapter(tasks);
                recyclerView.setAdapter(taskAdapter);
            });
        }).start();
    }


    private void setupInsertButton(int user_id, String username) {
        InsertButton.setOnClickListener(v -> {
            Intent insetTask = new Intent(getApplicationContext(), InsertActivity.class);
            insetTask.putExtra("user_id", String.valueOf(user_id)); // Convert to String to avoid null issues
            insetTask.putExtra("username", username);
            startActivity(insetTask);
        });
    }


}