package edu.skku.cs.dokkang.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.data_models.request.SignUpRequest;
import edu.skku.cs.dokkang.data_models.response.SignUpResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // sign-up information
        EditText signup_id = findViewById(R.id.signup_idEditText);
        EditText signup_pw = findViewById(R.id.signup_pwEditText);
        EditText signup_nickname = findViewById(R.id.nnEditText);
        EditText signup_department = findViewById(R.id.dpEditText);
        EditText signup_email = findViewById(R.id.emailEditText);

        // sign-up button
        Button signup_btn = findViewById(R.id.loginButton);

        signup_btn.setOnClickListener(view -> {


            String id = signup_id.getText().toString();
            String pw = signup_pw.getText().toString();
            String email = signup_email.getText().toString();
            String nickname = signup_nickname.getText().toString();
            String department = signup_department.getText().toString();

            /*
            checking input
            1. blank
            2. length of the password < 6
             */

            if (id.length() == 0 || pw.length() == 0 || email.length() == 0 || nickname.length() == 0 || department.length() == 0) {
                Toast.makeText(SignUp.this, "Please fill in the blank", Toast.LENGTH_SHORT).show();
            } else if (pw.length() < 6) {
                Toast.makeText(SignUp.this, "Please enter a password of at least six digits", Toast.LENGTH_SHORT).show();
            } else {
                SignUpRequest data = new SignUpRequest();
                data.setUsername(id);
                data.setEmail(email);
                data.setPassword(pw);

                Gson gson = new Gson();
                String json = gson.toJson(data, SignUpRequest.class);

                new RestAPICaller().Post(Constant.SERVER_BASE_URI + "/user",
                    json,
                    new RestAPICaller.ApiCallback<SignUpResponse>(
                        SignUp.this,
                        SignUpResponse.class,
                        res -> {
                            SignUp.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignUp.this, res.getStatus(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            finish();
                        }
                    )
                );
            }
        });
    }
}