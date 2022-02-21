package edu.neu.loginexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText name, email, password;
    Button registerButton;
    TextView loginButton;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name_entry);
        email = findViewById(R.id.email_entry);
        password = findViewById(R.id.password_entry);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.already_registered);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        checkIfLoggedIn();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                if(TextUtils.isEmpty(emailStr)) {
                    email.setError("Please enter your email");
                }
                else if(TextUtils.isEmpty(passwordStr)) {
                    password.setError("Please enter your password");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(Register.this,
                                        "Successfully Registered", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),
                                        MainActivity.class));
                            } else {
                                Toast.makeText(Register.this,
                                        "Unsuccessful Registration", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    public void checkIfLoggedIn() {
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
