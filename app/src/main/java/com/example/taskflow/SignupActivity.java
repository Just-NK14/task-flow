package com.example.taskflow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


public class SignupActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button signupButton;
    TextView alreadyAccountTextView;

    AppDatabase AppDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup_button);
        alreadyAccountTextView = findViewById(R.id.acc_exists);

        AppDB = AppDatabase.getInstance(this);

        signupButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Run database operations in a background thread
            new Thread(() -> {
                User existingUser = AppDB.userDao().getUserByUsername(username);
                if (existingUser != null) {
                    runOnUiThread(() -> {
                        alreadyAccountTextView.setAlpha(1);
                        alreadyAccountTextView.setTextColor(ContextCompat.getColorStateList(this, R.color.error));
                        Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                    });
                    return;
                }

                User user = new User(username, password);
                AppDB.userDao().addUser(user);

                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), "Signup successful! Please log in.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                    finish();
                });
            }).start();
        });


    }

}