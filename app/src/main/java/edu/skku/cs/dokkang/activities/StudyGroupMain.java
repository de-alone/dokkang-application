package edu.skku.cs.dokkang.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.adapters.StudyGroupPostListViewAdapter;
import edu.skku.cs.dokkang.data_models.StudyGroupPost;

public class StudyGroupMain extends AppCompatActivity {
    private ListView listView;

    private StudyGroupPostListViewAdapter listViewAdapter;
    private ArrayList<StudyGroupPost> study_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_main);

        Intent intent = getIntent();
        String lecture = intent.getStringExtra("lecture");
        String professor = intent.getStringExtra("professor");
        int lecture_id = intent.getIntExtra("lecture_id", 0);
        String lecture_no = intent.getStringExtra("lecture_no");

        TextView lecture_name = findViewById(R.id.sg_subjectNameView);
        lecture_name.setText(lecture);

        study_groups = new ArrayList<>();
        StudyGroupPost post1 = new StudyGroupPost("기말고사 공부", 7, 0, 2, 5, "2022-11-30 5pm", "도서관 1층");
        StudyGroupPost post2 = new StudyGroupPost("testing 공부", 10, 0, 6, 6, "2022-11-21 2pm", "A카페");
        post1.setContent("같이 기말고사 대비 공부하실 분들 환영합니다~");
        post2.setContent("sw testing이 어려워서 같이 공부하실 분들 찾아요");
        study_groups.add(post1);
        study_groups.add(post2);

        listView = findViewById(R.id.postlistView);
        listViewAdapter = new StudyGroupPostListViewAdapter(study_groups, getApplicationContext());
        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final StudyGroupPost item = (StudyGroupPost) listViewAdapter.getItem(i);
                Intent detail_intent = new Intent(StudyGroupMain.this, StudyGroupDetails.class);
                detail_intent.putExtra("studygroup", (Serializable) item);
                detail_intent.putExtra("lecture", lecture);
                startActivity(detail_intent);
            }
        });

        FloatingActionButton add_btn = findViewById(R.id.sg_addPostButton);
        add_btn.setOnClickListener(view -> {
            Intent np_intent = new Intent(StudyGroupMain.this, StudyGroupNewPost.class);
            startActivity(np_intent);
        });
    }
}