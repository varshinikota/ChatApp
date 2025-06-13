package com.example.chatapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button registerBtn;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        usernameInput = findViewById(R.id.editNewUsername);
        passwordInput = findViewById(R.id.editNewPassword);
        registerBtn = findViewById(R.id.registerButton);

        registerBtn.setOnClickListener(v -> {
            String uname = usernameInput.getText().toString().trim();
            String pwd = passwordInput.getText().toString().trim();

            if (uname.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor c = db.getAllUsersExcept("");
            boolean exists = false;
            while (c.moveToNext()) {
                if (c.getString(0).equals(uname)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
            } else {
                db.insertUser(uname, pwd);
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });
    }
}
