package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.MySubjectListViewAdapter;
import edu.skku.cs.dokkang.data_models.MySubject;
import edu.skku.cs.dokkang.data_models.response.LectureResponse;
import edu.skku.cs.dokkang.utils.Credential;

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
        new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/user/" + user_id + "/lectures",
            new RestAPICaller.ApiCallback<LectureResponse>(
                    MyPage.this,
                    LectureResponse.class,
                    data -> {
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
            )
        );

        /*personal infomation button click events*/
        personalInfo_btn.setOnClickListener(view -> {
            Intent pinfo_intent = new Intent(MyPage.this, PersonalInfo.class);
            pinfo_intent.putExtra("user_id", user_id);
            pinfo_intent.putExtra("token", token);
            startActivity(pinfo_intent);
        });

        /* logout */
        logout_btn.setOnClickListener(view -> {
            new Credential(this).resetCredentials();

            MyPage.this.finish();
            Intent main_intent = new Intent(MyPage.this, MainActivity.class);
            startActivity(main_intent);
        });
    }
}
