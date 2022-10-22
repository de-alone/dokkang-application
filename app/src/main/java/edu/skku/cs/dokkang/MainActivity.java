package edu.skku.cs.dokkang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* login info: ID, Password */
        EditText idEditText = findViewById(R.id.idEditText);
        EditText pwEditText = findViewById(R.id.pwEditText);

        /* login button */
        Button loginButton = findViewById(R.id.loginButton);
        /* login - onClick */
        loginButton.setOnClickListener(view -> {

        });

        /* signup button */
        Button signupButton = findViewById(R.id.signupButton);
        /* signup - onClick */
        signupButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });
    }
}