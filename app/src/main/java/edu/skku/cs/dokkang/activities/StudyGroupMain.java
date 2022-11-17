package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.LecturePostListViewAdapter;
import edu.skku.cs.dokkang.adapters.StudyGroupPostListViewAdapter;
import edu.skku.cs.dokkang.data_models.LecturePost;
import edu.skku.cs.dokkang.data_models.StudyGroupPost;
import edu.skku.cs.dokkang.data_models.response.PostListResponse;
import edu.skku.cs.dokkang.data_models.response.StudyGroupListResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class StudyGroupMain extends AppCompatActivity {

    public static Activity StudyGroup_activity;
    private ListView listView;
    private Boolean endOfPost = false;
    public String before = null;
    private static final String postFetchLimit = "10";
    private static Boolean updating = false;
    private StudyGroupPostListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_main);

        Intent intent = getIntent();
        long lecture_id = intent.getLongExtra("lecture_id", -1);
        String lecture = intent.getStringExtra("lecture");

        TextView lecture_name = findViewById(R.id.sg_subjectNameView);
        lecture_name.setText(lecture);
        ImageButton refresh = findViewById(R.id.studyGroupRefreshButton);

        refresh.setOnClickListener(view -> {
            finish();
            overridePendingTransition(0, 0);
            Intent new_intent = getIntent();
            startActivity(new_intent);
            overridePendingTransition(0, 0);
        });

        StudyGroup_activity = StudyGroupMain.this;
        listView = findViewById(R.id.sg_postlistView);
        listViewAdapter = new StudyGroupPostListViewAdapter(Collections.emptyList(), getApplicationContext());
        listView.setAdapter(listViewAdapter);

        listView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 스크롤이 최하단이 아니거나 이미 모든 글을 불러왔으면 종료
                if (endOfPost || StudyGroupMain.updating || view.canScrollVertically(1)) {
                    return;
                }
                updating = true;

                Pair<Long, String> credential = new Credential(StudyGroup_activity).loadCredentials();
                Long user_id = credential.first;
                String token = credential.second;

                String URI = Constant.SERVER_BASE_URI + "/lecture/" + String.valueOf(lecture_id) + "/studygroups";
                Map<String, String> queries = new HashMap<>();
                queries.put("limit", postFetchLimit);
                if (before != null) {
                    queries.put("before", before);
                }
                new RestAPICaller(token).get(URI,
                        queries,
                        new RestAPICaller.ApiCallback<StudyGroupListResponse>(
                                StudyGroup_activity,
                                StudyGroupListResponse.class,
                                data -> {
                                    List<StudyGroupPost> posts = data.getStudygroups();

                                    listViewAdapter.addItems(posts);
                                    before = data.getBefore();

                                    StudyGroup_activity.runOnUiThread(listViewAdapter::notifyDataSetChanged);

                                    if (posts.size() == 0) {
                                        endOfPost = true;
                                    }

                                    updating = false;
                                }
                        ));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final StudyGroupPost item = (StudyGroupPost) listViewAdapter.getItem(i);
                Intent detail_intent = new Intent(StudyGroup_activity, StudyGroupDetails.class);
                detail_intent.putExtra("lecture", lecture);
                detail_intent.putExtra("PostId", item.getId());
                startActivity(detail_intent);
            }
        });

        FloatingActionButton add_btn = findViewById(R.id.sg_addPostButton);
        add_btn.setOnClickListener(view -> {
            Intent np_intent = new Intent(StudyGroup_activity, StudyGroupNewPost.class);
            np_intent.putExtra("lecture_id", lecture_id);
            np_intent.putExtra("lecture", lecture);
            startActivity(np_intent);
        });
    }
}