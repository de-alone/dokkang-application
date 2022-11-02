package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.adapters.MySubjectListViewAdapter;
import edu.skku.cs.dokkang.data_models.MySubject;
import edu.skku.cs.dokkang.data_models.response.LectureResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyPage extends AppCompatActivity {

    public static Activity MyPage_activity;

    private ListView listView;

    private MySubjectListViewAdapter listViewAdapter;
    private ArrayList<MySubject> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Button subject_edit_btn = findViewById(R.id.editSubjectbutton);
        Button personalInfo_btn = findViewById(R.id.pInfobutton);
        Button logout_btn = findViewById(R.id.LogOutbutton);
        listView = findViewById(R.id.commentListView);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        long user_id = intent.getLongExtra("user_id", 0);

        MyPage_activity = MyPage.this;


        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.dokkang.tk/user/" + user_id + "/lectures").newBuilder();
        String url = urlBuilder.build().toString();
        Request req = new Request.Builder().url(url)
                .addHeader("Authorization", "Bearer " + token)
                .get().build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) { // can't receive any response from server
                MyPage.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyPage.this, "server failed", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                /* response */
                final String res = response.body().string();

                Gson gson = new GsonBuilder().create();
                final LectureResponse data = gson.fromJson(res, LectureResponse.class);

                MyPage.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyPage.this, data.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (response.isSuccessful()) {
                    List<MySubject> lectures = data.getLectures();
                    ArrayList<String> check_lecture_nos = new ArrayList<>();

                    for (MySubject subject : lectures) {
                        check_lecture_nos.add(subject.getNo());
                    }

                    MyPage.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* show list of my lectures*/
                            listViewAdapter = new MySubjectListViewAdapter(lectures, getApplicationContext());
                            listView.setAdapter(listViewAdapter);

                            /*subject edit button click events*/
                            subject_edit_btn.setOnClickListener(view -> {
                                Intent se_intent = new Intent(MyPage.this, SubjectEdit.class);
                                se_intent.putExtra("user_id", user_id);
                                se_intent.putExtra("token", token);
                                se_intent.putExtra("checked_lecture_no", check_lecture_nos);
                                startActivity(se_intent);

                            });
                        }
                    });
                }
            }
        });

        /*personal infomation button click events*/
        personalInfo_btn.setOnClickListener(view -> {
            Intent pinfo_intent = new Intent(MyPage.this, PersonalInfo.class);
            pinfo_intent.putExtra("user_id", user_id);
            pinfo_intent.putExtra("token", token);
            startActivity(pinfo_intent);
        });
    }

    private void getAllLectures() {

    }
}
