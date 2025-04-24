package com.example.taskflow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
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

public class login extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView signUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase AppDB = AppDatabase.getInstance(this);

        signUpTextView = findViewById(R.id.acc_exists);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        signUpTextView.setText(getClickableSpan()); // Set the formatted text
        signUpTextView.setMovementMethod(LinkMovementMethod.getInstance()); // Enable clicks

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                usernameEditText.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                passwordEditText.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
                usernameEditText.setText("");  // Reset the text
                passwordEditText.setText("");  // Reset the text

                Log.d("Login", "Login Failed: Empty fields");
                Toast.makeText(getApplicationContext(), ContextCompat.getString(this, R.string.no_empty), Toast.LENGTH_SHORT).show();
                return;  // Exit from the method if fields are empty
            }

            // Run the login operation in a background thread
            new Thread(() -> {
                // Try to get the user from the database
                User loggedInUser = AppDB.userDao().login(username, password);

                // Switch back to the UI thread to handle UI updates
                runOnUiThread(() -> {
                    if (loggedInUser == null) {  // If no user is found, show login failure message
                        Log.d("Login", "Login Failed: User not found");
                        Toast.makeText(getApplicationContext(), ContextCompat.getString(this, R.string.login_error), Toast.LENGTH_SHORT).show();
                    } else {  // If user is found, show login success message and navigate to HomeActivity
                        Log.d("Login", "Login Successful");
                        Toast.makeText(getApplicationContext(), ContextCompat.getString(this, R.string.login_success), Toast.LENGTH_SHORT).show();

                        // Pass the username to the home activity
                        Intent homeActivity = new Intent(this, HomeActivity.class);
                        homeActivity.putExtra("username", username);
                        startActivity(homeActivity);
                        finish();
                    }
                });
            }).start();
        });



    }

    private SpannableString getClickableSpan() {
        String fullText = ContextCompat.getString(this, R.string.no_acc);
        SpannableString spannableString = new SpannableString(fullText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(login.this, SignupActivity.class));
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.BLUE); // Make "Create one" blue
                ds.setUnderlineText(false); // Remove underline if not needed
            }
        };

        int startIndex = fullText.indexOf("Create one");
        int endIndex = startIndex + "Create one".length();
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }

}