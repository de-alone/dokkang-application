package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Optional;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.LecturePostListViewAdapter;
import edu.skku.cs.dokkang.adapters.MySubjectListViewAdapter;
import edu.skku.cs.dokkang.data_models.LecturePost;
import edu.skku.cs.dokkang.data_models.MySubject;
import edu.skku.cs.dokkang.data_models.StudyGroupPost;
import edu.skku.cs.dokkang.data_models.response.LectureResponse;
import edu.skku.cs.dokkang.data_models.response.PostDetailResponse;
import edu.skku.cs.dokkang.data_models.response.PostListResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class CommunityMain extends AppCompatActivity {

    public static Activity Community_activity;
    private ListView listView;
    private LecturePostListViewAdapter listViewAdapter;
    private Boolean endOfPost = false;
    public String before = null;
    private static final String postFetchLimit = "10";
    private static Boolean updating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_main);

        Intent intent = getIntent();
        long lecture_id = intent.getLongExtra("lecture_id", -1);
        String lecture = intent.getStringExtra("lecture");

        TextView lectureNameView = findViewById(R.id.subjectNameView);
        lectureNameView.setText(lecture);

        FloatingActionButton add_btn = findViewById(R.id.addPostButton);
        listView = findViewById(R.id.postlistView);

        Community_activity = CommunityMain.this;

        listViewAdapter = new LecturePostListViewAdapter(Collections.emptyList(), getApplicationContext());
        listView.setAdapter(listViewAdapter);

        add_btn.setOnClickListener(view -> {
            Intent add_intent = new Intent(Community_activity, NewPost.class);
            add_intent.putExtra("lecture_id", lecture_id);
            add_intent.putExtra("lecture", lecture);
            startActivity(add_intent);
        });

        listView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // 스크롤이 최하단이 아니거나 이미 모든 글을 불러왔으면 종료
                if (endOfPost || CommunityMain.updating || view.canScrollVertically(1)) {
                    return;
                }
                updating = true;

                Pair<Long, String> credential = new Credential(CommunityMain.this).loadCredentials();
                Long user_id = credential.first;
                String token = credential.second;

                String URI = Constant.SERVER_BASE_URI + "/lecture/" + String.valueOf(lecture_id) + "/posts";
                Map<String, String> queries = new HashMap<>();
                queries.put("limit", postFetchLimit);
                if (before != null) {
                    queries.put("before", before);
                }
                new RestAPICaller(token).get(URI,
                        queries,
                        new RestAPICaller.ApiCallback<PostListResponse>(
                            CommunityMain.this,
                            PostListResponse.class,
                            data -> {
                                List<LecturePost> posts = data.getPosts();

                                listViewAdapter.addItems(posts);
                                before = data.getBefore();

                                CommunityMain.this.runOnUiThread(listViewAdapter::notifyDataSetChanged);

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
                final LecturePost item = (LecturePost) listViewAdapter.getItem(i);

                Pair<Long, String> credential = new Credential(CommunityMain.this).loadCredentials();
                String token = credential.second;

                new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/post/" + item.getId(),
                        new RestAPICaller.ApiCallback<PostDetailResponse>(
                                CommunityMain.this,
                                PostDetailResponse.class,
                                data -> {
                                    Intent pd_intent = new Intent(CommunityMain.this, PostDetails.class);
                                    pd_intent.putExtra("post", (Serializable) data);
                                    pd_intent.putExtra("lecture", lecture);
                                    startActivity(pd_intent);
                                }
                        )
                );
            }
        });
    }
}