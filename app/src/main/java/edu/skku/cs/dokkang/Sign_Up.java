package edu.skku.cs.dokkang;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_Up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // sign-up information
        EditText signup_id = findViewById(R.id.signup_idEditText);
        EditText signup_pw = findViewById(R.id.signup_pwEditText);
        EditText signup_nickname = findViewById(R.id.nnEditText);
        EditText signup_department = findViewById(R.id.dpEditText);

        // sign-up button
        Button signup_btn = findViewById(R.id.loginButton);

        signup_btn.setOnClickListener(view -> {
            /* 서버와 통신하는 부분 */

            boolean result = true;
            /* 서버와 통신하는 부분 */

            if(result) {
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_SHORT).show();
            }

        });

    }




}