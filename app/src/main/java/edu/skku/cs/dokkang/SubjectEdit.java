package edu.skku.cs.dokkang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SubjectEdit extends AppCompatActivity {

    private ListView listView;
    private EditSubjectListViewAdapter listViewAdapter;
    private List<MySubject> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_edit);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        long user_id = intent.getLongExtra("user_id",0);
        List<String> checked_lecture_nos = intent.getStringArrayListExtra("checked_lecture_no");

        Button confirm_btn = findViewById(R.id.confirmButton);
        listView = findViewById(R.id.subjectListView);


        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.dokkang.tk/lectures").newBuilder();
        String url = urlBuilder.build().toString();
        Request req = new Request.Builder().url(url)
                .addHeader("Authorization", "Bearer " + token)
                .get().build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) { // can't receive any response from server
                SubjectEdit.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SubjectEdit.this, "failed to get all lectures", Toast.LENGTH_SHORT).show();
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                /* response */
                final String res = response.body().string();

                Gson gson = new GsonBuilder().create();
                final LectureDataModel data = gson.fromJson(res, LectureDataModel.class);

                items = new ArrayList<>();
                for (MySubject lecture : data.getLectures()) {
                    if (checked_lecture_nos.contains(lecture.getNo())) {
                        lecture.setChecked(true);
                    }
                    items.add(lecture);
                }

                if (response.isSuccessful()) {
                    SubjectEdit.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* show list of my lectures*/
                            listViewAdapter = new EditSubjectListViewAdapter(items, getApplicationContext());
                            listView.setAdapter(listViewAdapter);
                        }
                    });
                }
            }
        });

        /*confirm button*/
        confirm_btn.setOnClickListener(view -> {
            /*items 안의 checked 확인해서 check된 과목들을 서버에 등록 요청보냄*/
            List<Integer> checked_lecture_ids = new ArrayList<>();

            for(MySubject item : items) {
                if(item.getChecked()) {
                    checked_lecture_ids.add(item.getId());
                }
            }

            Gson gson = new Gson();
            String payload = gson.toJson(Map.of("lecture_ids", checked_lecture_ids));

            HttpUrl.Builder updateUrlBuilder = HttpUrl.parse("https://api.dokkang.tk/user/"+ user_id +"/lectures").newBuilder();
            String updateUrl = updateUrlBuilder.build().toString();
            Request updateRequest = new Request.Builder().url(updateUrl)
                    .addHeader("Authorization", "Bearer " + token)
                    .put(RequestBody.create(MediaType.parse("application/json"), payload)).build();

            OkHttpClient updateClient = new OkHttpClient();

            Log.d("DEBUG", updateRequest.toString());
            updateClient.newCall(updateRequest).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) { // can't receive any response from server
                    SubjectEdit.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SubjectEdit.this, "failed to request updating selected lectures", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    SubjectEdit.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()) {
                                Toast.makeText(SubjectEdit.this, "updated selected lectures!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SubjectEdit.this, "failed to update selected lectures with " + response.body().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

            finish();
        });
    }
}