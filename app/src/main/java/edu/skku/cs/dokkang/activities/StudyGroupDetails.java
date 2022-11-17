package edu.skku.cs.dokkang.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.CommentListViewAdapter;
import edu.skku.cs.dokkang.data_models.request.StudyGroupLikeRequest;
import edu.skku.cs.dokkang.data_models.request.StudyGroupNewCommentRequest;
import edu.skku.cs.dokkang.data_models.response.NewStudyGroupResponse;
import edu.skku.cs.dokkang.data_models.response.StudyGroupDetailResponse;
import edu.skku.cs.dokkang.data_models.response.UserDetailResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class StudyGroupDetails extends AppCompatActivity {

    public Activity StudyGroupDetails_activity;
    private ListView comment_listView;
    private CommentListViewAdapter listViewAdapter;

    private List<String> participants;
    private long writer;
    private boolean full;
    private boolean already;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studygroup_details);

        Intent intent = getIntent();
        String lecture = intent.getStringExtra("lecture");
        Long studygroupID = intent.getLongExtra("PostId", -1);

        StudyGroupDetails_activity = StudyGroupDetails.this;

        if (studygroupID == -1) {
            StudyGroupDetails_activity.runOnUiThread(() -> Toast.makeText(StudyGroupDetails_activity, "게시글을 불러오는데 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show());
            finish();
        }

        Pair<Long, String> credential = new Credential(StudyGroupDetails_activity).loadCredentials();
        Long user_id = credential.first;
        String token = credential.second;

        new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/studygroup/" + studygroupID,
                new RestAPICaller.ApiCallback<StudyGroupDetailResponse>(
                        StudyGroupDetails_activity,
                        StudyGroupDetailResponse.class,
                        studygroup -> {
                            StudyGroupDetails_activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView lecture_name = findViewById(R.id.sgLectureNameView);
                                    lecture_name.setText(lecture);

                                    TextView user = findViewById(R.id.sg_name_textView);
                                    user.setText(studygroup.getUsername());

                                    TextView time = findViewById(R.id.sg_time_textView);
                                    time.setText(studygroup.getCreated_at());

                                    TextView title = findViewById(R.id.sg_title_textView);
                                    title.setText(studygroup.getTitle());

                                    TextView content = findViewById(R.id.sg_content_textView);
                                    content.setText(studygroup.getContent());

                                    TextView num_like = findViewById(R.id.sg_liketextview);
                                    num_like.setText(Integer.toString(studygroup.getNum_likes()));

                                    TextView comments = findViewById(R.id.sgCommenttextview);
                                    comments.setText(Integer.toString(studygroup.getComments().toArray().length));

                                    TextView date = findViewById(R.id.dateTextview);
                                    date.setText("시간: " + studygroup.getStudytime());

                                    TextView place = findViewById(R.id.placeTextview);
                                    place.setText("장소: " + studygroup.getStudyplace());

                                    TextView member = findViewById(R.id.memberTextview);
                                    member.setText("모집인원: " + studygroup.getStudycapacity());

                                    TextView currentMember = findViewById(R.id.currentMember);
                                    currentMember.setText("인원: " + studygroup.getParticipants().size() + "/" + studygroup.getStudycapacity());

                                    comment_listView = findViewById(R.id.sgCommentListView);
                                    listViewAdapter = new CommentListViewAdapter(studygroup.getComments(), getApplicationContext(), StudyGroupDetails_activity);
                                    comment_listView.setAdapter(listViewAdapter);
                                }
                            });
                            setParticipants(studygroup.getParticipants());
                            setWriter(studygroup.getUser_id());
                            setFull(studygroup.getParticipants().size() == studygroup.getStudycapacity());
                        }
                )
        );

        Button like_btn = findViewById(R.id.sg_likebutton);

        like_btn.setOnClickListener(view -> {
            StudyGroupLikeRequest data = new StudyGroupLikeRequest();
            data.setUser_id(user_id);
            data.setStudygroup_id(studygroupID);

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/studygroup-like",
                    payload,
                    new RestAPICaller.ApiCallback<NewStudyGroupResponse>(
                            StudyGroupDetails_activity,
                            NewStudyGroupResponse.class,
                            res -> {
                                if (res.getStatus().equals("ok")) {
                                    StudyGroupDetails_activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            overridePendingTransition(0, 0);
                                            Intent intent = getIntent();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                            Toast.makeText(StudyGroupDetails_activity, "Like", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                    ));
        });

        Button send_btn = findViewById(R.id.sg_send_button);

        send_btn.setOnClickListener(v -> {
            EditText commentEditText = findViewById(R.id.sgCommentEditText);
            String comment = commentEditText.getText().toString();

            if (comment.equals("")) {
                Toast.makeText(StudyGroupDetails_activity, "Please write comments.", Toast.LENGTH_SHORT).show();
                return;
            }

            StudyGroupNewCommentRequest data = new StudyGroupNewCommentRequest();
            data.setUser_id(user_id);
            data.setStudygroup_id(studygroupID);
            data.setContent(comment);

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/studygroup-comment",
                    payload,
                    new RestAPICaller.ApiCallback<NewStudyGroupResponse>(
                            StudyGroupDetails_activity,
                            NewStudyGroupResponse.class,
                            res -> {
                                if (res.getStatus().equals("ok")) {
                                    StudyGroupDetails_activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            /*새로고침 후 토스트 메시지 띄우기*/
                                            finish();
                                            overridePendingTransition(0, 0);
                                            Intent intent = getIntent();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                            Toast.makeText(StudyGroupDetails_activity, "add comment", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                    ));

        });

        Button participate_btn = findViewById(R.id.participate_btn);

        participate_btn.setOnClickListener(view -> {
            if (user_id == getWriter()) {
                Toast.makeText(StudyGroupDetails_activity, "You're the head of this study group!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isFull()) {
                Toast.makeText(StudyGroupDetails_activity, "This study group is closed.", Toast.LENGTH_SHORT).show();
                return;
            }

            new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/user/" + user_id,
                    new RestAPICaller.ApiCallback<UserDetailResponse>(
                            StudyGroupDetails_activity,
                            UserDetailResponse.class,
                            data -> {
                                if (getParticipants().contains(data.getUsername())) {
                                    setAlready(true);
                                }
                            }
                    )
            );

            if (isAlready()) {
                Toast.makeText(StudyGroupDetails_activity, "You have already participated!", Toast.LENGTH_SHORT).show();
                return;
            }

            StudyGroupLikeRequest data = new StudyGroupLikeRequest();
            data.setUser_id(user_id);
            data.setStudygroup_id(studygroupID);

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/studygroup-participant",
                    payload,
                    new RestAPICaller.ApiCallback<NewStudyGroupResponse>(
                            StudyGroupDetails_activity,
                            NewStudyGroupResponse.class,
                            res -> {
                                if (res.getStatus().equals("ok")) {
                                    StudyGroupDetails_activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            finish();
                                            overridePendingTransition(0, 0);
                                            Intent intent = getIntent();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                            Toast.makeText(StudyGroupDetails_activity, "Like", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                    ));
        });
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public long getWriter() {
        return writer;
    }

    public void setWriter(long writer) {
        this.writer = writer;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public boolean isAlready() {
        return already;
    }

    public void setAlready(boolean already) {
        this.already = already;
    }
}