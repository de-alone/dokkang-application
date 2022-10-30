package edu.skku.cs.dokkang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SubjectEdit extends AppCompatActivity {

    private ListView listView;
    private EditSubjectListViewAdapter listViewAdapter;
    private ArrayList<Subject> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_edit);

        Intent intent = getIntent();
        String token = intent.getStringExtra("token");
        long user_id = intent.getLongExtra("user_id",0);

        Button confirm_btn = findViewById(R.id.confirmButton);
        listView = findViewById(R.id.subjectListView);


        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/

        // test set  (나중에 삭제해주세요)
        items = new ArrayList<>();
        Subject TestLecture1 = new Subject("인공지능개론", "박진영", 1, 1, false);
        Subject TestLecture2 = new Subject("소프트웨어공학개론", "차수영", 2, 2, false);
        items.add(TestLecture1);
        items.add(TestLecture2);
        /* 서버에 강의 목록 요청해서 items arraylist에 삽입*/


        /* show list of my lectures*/
        listViewAdapter = new EditSubjectListViewAdapter(items, getApplicationContext());
        listView.setAdapter(listViewAdapter);

        /*confirm button*/
        confirm_btn.setOnClickListener(view -> {
            /*items 안의 checked 확인해서 check된 과목들을 서버에 등록 요청보냄*/
            // 아래 코드는 제대로 작동하는지 테스트용으로 작성했습니다. 서버 요청 구현할 떄 삭제해주세요
            String message = "";
            Log.i("debug", "check");
            for(int i = 0; i < items.size(); i++) {
                if(items.get(i).checked) {
                    Log.i("debug", "check");
                    message = message + items.get(i).lecture_name;
                    message = message + ", ";
                }
            }
            Toast.makeText(SubjectEdit.this, message, Toast.LENGTH_SHORT).show();
            /*items 안의 checked 확인해서 check된 과목들을 서버에 등록 요청보냄*/

            finish();
        });
    }
}