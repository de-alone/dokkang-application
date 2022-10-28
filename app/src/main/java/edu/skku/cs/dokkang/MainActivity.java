package edu.skku.cs.dokkang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

            LoginDataModel data = new LoginDataModel();
            data.setUsername(id);
            data.setPassword(pw);

            Gson gson = new Gson();
            String json = gson.toJson(data, LoginDataModel.class);

            HttpUrl.Builder urlBuilder = HttpUrl.parse("http://db.dokkang.tk:8080").newBuilder();
            String url = urlBuilder.build().toString();

            Request req = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), json)).build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    /* response */
                    final String res = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Gson gson = new GsonBuilder().create();
                    final LoginResponseDataModel data = gson.fromJson(res, LoginResponseDataModel.class);

                    if (data.getStatus() == "ok") {
                        String message = "Login success";

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(MainActivity.this, SignUp.class);
                        startActivity(intent);
                    }
                    else {
                        final int status = response.code();
                        String message = "";

                        if (status == 404) {
                            message += "User id does not exist";
                        } else if (status == 401) {
                            message += "Password not matched";
                        } else {
                            message += "Login failed";
                        }

                        String finalMessage = message;
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalMessage, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            });
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