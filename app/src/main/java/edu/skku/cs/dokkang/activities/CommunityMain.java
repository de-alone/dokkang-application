package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.LecturePostListViewAdapter;
import edu.skku.cs.dokkang.adapters.MySubjectListViewAdapter;
import edu.skku.cs.dokkang.data_models.LecturePost;
import edu.skku.cs.dokkang.data_models.MySubject;
import edu.skku.cs.dokkang.data_models.response.LectureResponse;

public class CommunityMain extends AppCompatActivity {

    public static Activity Community_activity;
    private ListView listView;
    private LecturePostListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_main);

        Intent intent = getIntent();
        long lecture_id = intent.getLongExtra("lecture_id", 0);

        listView = findViewById(R.id.postListView);

        Community_activity = CommunityMain.this;

        listViewAdapter = new LecturePostListViewAdapter(null, getApplicationContext());
        listView.setAdapter(listViewAdapter);
    }
}