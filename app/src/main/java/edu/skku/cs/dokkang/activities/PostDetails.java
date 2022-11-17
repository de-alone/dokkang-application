package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.Serializable;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.adapters.CommentListViewAdapter;
import edu.skku.cs.dokkang.data_models.request.LikeRequest;
import edu.skku.cs.dokkang.data_models.request.NewCommentRequest;
import edu.skku.cs.dokkang.data_models.request.NewPostRequest;
import edu.skku.cs.dokkang.data_models.request.SignUpRequest;
import edu.skku.cs.dokkang.data_models.response.NewPostResponse;
import edu.skku.cs.dokkang.data_models.response.PostDetailResponse;
import edu.skku.cs.dokkang.data_models.response.SignUpResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class PostDetails extends AppCompatActivity {

    public static Activity PostDetail_activity;
    private ListView comment_listView;
    private CommentListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        Intent intent = getIntent();
        String lecture = intent.getStringExtra("lecture");
        Long postID = intent.getLongExtra("PostId", -1);

        if (postID == -1) {
            PostDetails.this.runOnUiThread(() -> Toast.makeText(PostDetails.this, "게시글을 불러오는데 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show());
            finish();
        }

        Pair<Long, String> credential = new Credential(PostDetails.this).loadCredentials();
        Long user_id = credential.first;
        String token = credential.second;

        PostDetail_activity = PostDetails.this;

        /*서버로부터 게시글 세부 정보 가져와서 보여줌*/
        new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/post/" + postID,
                new RestAPICaller.ApiCallback<PostDetailResponse>(
                        PostDetails.this,
                        PostDetailResponse.class,
                        post -> {
                            PostDetails.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView lecture_name = findViewById(R.id.postLectureNameView);
                                    lecture_name.setText(lecture);

                                    TextView user = findViewById(R.id.post_name_textView);
                                    user.setText(post.getUsername());

                                    TextView time = findViewById(R.id.post_time_textView);
                                    time.setText(post.getCreated_at());

                                    TextView title = findViewById(R.id.post_title_textView);
                                    title.setText(post.getTitle());

                                    TextView content = findViewById(R.id.post_content_textView);
                                    content.setText(post.getContent());

                                    TextView num_like = findViewById(R.id.post_liketextview);
                                    num_like.setText("Like: " + post.getNum_likes());

                                    TextView comments = findViewById(R.id.postCommenttextview);
                                    comments.setText("Comments: " + post.getComments().toArray().length);

                                    comment_listView = findViewById(R.id.postCommentListView);
                                    listViewAdapter = new CommentListViewAdapter(post.getComments(), getApplicationContext(), PostDetail_activity);
                                    comment_listView.setAdapter(listViewAdapter);
                                }
                            });

                        }
                )
        );

        // 좋아요 버튼 구현
        Button like_btn = findViewById(R.id.post_likebutton);

        like_btn.setOnClickListener(v -> {


            LikeRequest data = new LikeRequest();
            data.setUser_id(user_id);
            data.setPost_id(postID);

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/like",
                    payload,
                    new RestAPICaller.ApiCallback<NewPostResponse>(
                            PostDetails.this,
                            NewPostResponse.class,
                            res -> {
                                if (res.getStatus().equals("ok")) {
                                    PostDetails.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            /*새로고침 후 토스트 메시지 띄우기*/
                                            finish();
                                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                                            Intent intent = getIntent();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                                            Toast.makeText(PostDetails.this, "Like", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                    ));
        });

        // 댓글 전송 버튼 구현
        Button send_btn = findViewById(R.id.post_send_button);

        send_btn.setOnClickListener(v -> {
            EditText commentEditText = findViewById(R.id.postCommentEditText);
            String comment = commentEditText.getText().toString();

            if (comment.equals("")) {
                Toast.makeText(PostDetail_activity, "Please write comments.", Toast.LENGTH_SHORT).show();
                return;
            }

            NewCommentRequest data = new NewCommentRequest();
            data.setUser_id(user_id);
            data.setPost_id(postID);
            data.setContent(comment);

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/comment",
                    payload,
                    new RestAPICaller.ApiCallback<NewPostResponse>(
                            PostDetails.this,
                            NewPostResponse.class,
                            res -> {

                                if (res.getStatus().equals("ok")) {
                                    PostDetails.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            /*새로고침 후 토스트 메시지 띄우기*/
                                            finish();
                                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                                            Intent intent = getIntent();
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                                            Toast.makeText(PostDetails.this, "add comment", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                    ));

        });

    }
}
