package edu.skku.cs.dokkang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.EditSubjectListViewAdapter;
import edu.skku.cs.dokkang.data_models.MySubject;
import edu.skku.cs.dokkang.data_models.response.LectureResponse;

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
        long user_id = intent.getLongExtra("user_id", 0);
        List<String> checked_lecture_nos = intent.getStringArrayListExtra("checked_lecture_no");

        Button confirm_btn = findViewById(R.id.confirmButton);
        listView = findViewById(R.id.subjectListView);
        Intent mypage_intent = new Intent(SubjectEdit.this, MyPage.class);
        mypage_intent.putExtra("token", token);
        mypage_intent.putExtra("user_id", user_id);

        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/
        new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/lectures",
            new RestAPICaller.ApiCallback<LectureResponse>(
                SubjectEdit.this,
                LectureResponse.class,
                data -> {
                    items = new ArrayList<>();
                    for (MySubject lecture : data.getLectures()) {
                        if (checked_lecture_nos.contains(lecture.getNo())) {
                            lecture.setChecked(true);
                        }
                        items.add(lecture);
                    }

                    SubjectEdit.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* show list of my lectures*/
                            listViewAdapter = new EditSubjectListViewAdapter(items, getApplicationContext());
                            listView.setAdapter(listViewAdapter);
                        }
                    });
                }
            )
        );

        /*confirm button*/
        confirm_btn.setOnClickListener(view -> {
            /*items 안의 checked 확인해서 check된 과목들을 서버에 등록 요청보냄*/
            List<Integer> checked_lecture_ids = new ArrayList<>();

            for (MySubject item : items) {
                if (item.getChecked()) {
                    checked_lecture_ids.add(item.getId());
                }
            }

            Gson gson = new Gson();
            String payload = gson.toJson(Map.of("lecture_ids", checked_lecture_ids));

            new RestAPICaller(token).put(Constant.SERVER_BASE_URI + "/user/" + user_id + "/lectures",
                payload,
                new RestAPICaller.ApiCallback<Map>(
                    SubjectEdit.this,
                    Map.class,
                    text -> {
                        /* 이전에 실행한 MyPage activity와 현재 SubjectEdit activity를 종료하고 새로운 mypage activity를 실행*/
                        MyPage mypage_activity = (MyPage) MyPage.MyPage_activity;
                        mypage_activity.finish();
                        startActivity(mypage_intent);

                        SubjectEdit.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SubjectEdit.this, "updated selected lectures!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    }
                )
            );
        });
    }
}