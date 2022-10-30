package edu.skku.cs.dokkang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {

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
        long user_id = intent.getLongExtra("user_id",0);


        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/

        // test set  (나중에 삭제해주세요)
        items = new ArrayList<>();
        MySubject TestLecture1 = new MySubject("인공지능개론", "박진영", 1, 1);
        MySubject TestLecture2 = new MySubject("소프트웨어공학개론", "차수영", 2, 2);
        items.add(TestLecture1);
        items.add(TestLecture2);
        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/


        /* show list of my lectures*/
        listViewAdapter = new MySubjectListViewAdapter(items, getApplicationContext());
        listView.setAdapter(listViewAdapter);

        /*subject edit button click events*/
        subject_edit_btn.setOnClickListener(view -> {
            Intent se_intent = new Intent(MyPage.this, SubjectEdit.class);
            se_intent.putExtra("user_id", user_id);
            se_intent.putExtra("token", token);
            startActivity(se_intent);

        });

        /*personal infomation button click events*/
        personalInfo_btn.setOnClickListener(view -> {
            Intent pinfo_intent = new Intent(MyPage.this, PersonalInfo.class);
            pinfo_intent.putExtra("user_id", user_id);
            pinfo_intent.putExtra("token", token);
            startActivity(pinfo_intent);
        });

    }
}