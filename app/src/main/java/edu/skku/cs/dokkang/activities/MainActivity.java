package edu.skku.cs.dokkang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.data_models.response.LoginDataModel;
import edu.skku.cs.dokkang.data_models.response.LoginResponseDataModel;
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

            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.dokkang.tk/auth").newBuilder();
            String url = urlBuilder.build().toString();
            Request req = new Request.Builder().url(url).post(RequestBody.create(MediaType.parse("application/json"), json)).build();

            client.newCall(req).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {  // can't receive any response from server
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "server failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    /* response */
                    final String res = response.body().string();

                    Gson gson = new GsonBuilder().create();
                    final LoginResponseDataModel data = gson.fromJson(res, LoginResponseDataModel.class);

                    if (data.getStatus().equals("ok")) {
                        String message = "Login success";

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(MainActivity.this, MyPage.class);
                        /*  delivery */
                        intent.putExtra("token", data.getToken());
                        intent.putExtra("user_id", data.getUser_id());
                        startActivity(intent);
                        finish();
                    } else {
                        String message = "Login failed";

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
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