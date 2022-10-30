package edu.skku.cs.dokkang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyPage extends AppCompatActivity {

    private ListView listView;

    private MyLectureListViewAdapter listViewAdapter;
    private ArrayList<Lecture> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        long user_id = intent.getLongExtra("user_id",0);


        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/
        items = new ArrayList<>();
        Lecture TestLecture1 = new Lecture("인공지능개론", "박진영", 1, 1);
        Lecture TestLecture2 = new Lecture("소프트웨어공학개론", "차수영", 2, 2);
        items.add(TestLecture1);
        items.add(TestLecture2);
        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/

        listView = findViewById(R.id.commentListView);
        listViewAdapter = new MyLectureListViewAdapter(items, getApplicationContext());

        listView.setAdapter(listViewAdapter);




    }
}