package com.example.hospitalfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hospitalfinder.ui.login.LoginActivity;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText edUsername, edEmail, edPassword, edConfirmPassword;
    private Button btnRegister;
    private TextView tvExistingUser;

    private FirebaseAuth mAuth; // Firebase Authentication instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        edUsername = findViewById(R.id.editTextAppFullName);
        edEmail = findViewById(R.id.editTextAppAddress); // Email input field
        edPassword = findViewById(R.id.editTextAppContactNumber); // Password input field
        edConfirmPassword = findViewById(R.id.editTextAppFees); // Confirm password input field
        btnRegister = findViewById(R.id.buttonBookAppointment); // Register button
        tvExistingUser = findViewById(R.id.textViewExistingUser); // "Existing user" TextView

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        // Set click listener for the "Existing user? Login here" TextView
        tvExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Set click listener for the register button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPassword = edConfirmPassword.getText().toString().trim();

                // Validate user input
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (!isValid(password)) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters long and contain letters, digits, and special characters", Toast.LENGTH_LONG).show();
                } else {
                    // Proceed with Firebase registration
                    registerUser(email, password);
                }
            }
        });
    }

    /**
     * Registers a new user using Firebase Authentication.
     *
     * @param email    The email address of the user.
     * @param password The password of the user.
     */
    private void registerUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new com.google.android.gms.tasks.OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Registration successful! Please log in.", Toast.LENGTH_SHORT).show();

                            // Navigate to LoginActivity
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // Registration failed
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * Validates the password based on length, presence of letters, digits, and special characters.
     *
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    public static boolean isValid(String password) {
        boolean hasLetter = false, hasDigit = false, hasSpecialChar = false;

        if (password.length() < 8) {
            return false;
        }

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialChar = true;
            }

            // Early exit if all conditions are met
            if (hasLetter && hasDigit && hasSpecialChar) {
                return true;
            }
        }

        return hasLetter && hasDigit && hasSpecialChar;
    }
}
