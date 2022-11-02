package edu.skku.cs.dokkang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.data_models.request.LoginRequest;
import edu.skku.cs.dokkang.data_models.response.LoginResponse;
import okhttp3.OkHttpClient;

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
            String id = idEditText.getText().toString();
            String pw = pwEditText.getText().toString();

            if (id.equals("") || pw.equals("")) {
                Toast.makeText(getApplicationContext(), "Please fill in the blanks", Toast.LENGTH_SHORT).show();
                return;
            }

            OkHttpClient client = new OkHttpClient();

            LoginRequest data = new LoginRequest();
            data.setUsername(id);
            data.setPassword(pw);

            Gson gson = new Gson();
            String payload = gson.toJson(data, LoginRequest.class);

            new RestAPICaller().post(
                Constant.SERVER_BASE_URI + "/auth",
                payload,
                new RestAPICaller.ApiCallback<LoginResponse>(
                    MainActivity.this,
                    LoginResponse.class,
                    res -> {
                        String message = "Login success";

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(MainActivity.this, MyPage.class);
                        /*  delivery */
                        intent.putExtra("token", res.getToken());
                        intent.putExtra("user_id", res.getUser_id());
                        startActivity(intent);
                        finish();
                    }
                )
            );
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