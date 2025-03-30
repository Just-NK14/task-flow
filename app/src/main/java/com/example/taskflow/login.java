package com.example.taskflow;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {

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
    }

    public void onClick(View view) {
        String user = ((TextView)findViewById(R.id.username)).getText().toString();
        String pass = ((TextView)findViewById(R.id.password)).getText().toString();
        if(user.equals("admin") && pass.equals("admin")){
            System.out.println("Login Successful");
            Toast login_toast = Toast.makeText(this, ContextCompat.getString(this, R.string.login_success), Toast.LENGTH_SHORT);
            login_toast.show();
        }else{
            ((TextView)findViewById(R.id.username)).setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.error));
            ((TextView)findViewById(R.id.password)).setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.error));
            ((TextView)findViewById(R.id.username)).setText("");
            ((TextView)findViewById(R.id.password)).setText("");
            System.out.println("Login Failed");
            Toast login_toast = Toast.makeText(this, ContextCompat.getString(this, R.string.login_error), Toast.LENGTH_SHORT);
            login_toast.show();
        }
    }
}