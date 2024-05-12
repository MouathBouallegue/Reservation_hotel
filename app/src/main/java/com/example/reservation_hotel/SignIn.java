package com.example.reservation_hotel;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Toast;
import android.content.Intent;

public class SignIn extends AppCompatActivity {




    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignUp); // Note: The button ID is "buttonSignUp" in the XML

        buttonSignIn.setOnClickListener(v -> signIn());
    }

    private void signIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user!= null) {
                            // User is signed in
                            Toast.makeText(this, "Sign in success", Toast.LENGTH_SHORT).show();
                            // Navigate to the main activity or another activity
                            // For example, navigate to the MainActivity
                            Intent intent = new Intent(this,Home.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        // Sign in fails
                        Toast.makeText(this, "Sign in failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
