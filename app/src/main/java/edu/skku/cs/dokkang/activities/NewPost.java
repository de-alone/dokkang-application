package edu.skku.cs.dokkang.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.data_models.LecturePost;
import edu.skku.cs.dokkang.data_models.request.NewPostRequest;
import edu.skku.cs.dokkang.data_models.response.NewPostResponse;
import edu.skku.cs.dokkang.data_models.response.PostDetailResponse;
import edu.skku.cs.dokkang.data_models.response.PostListResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class NewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        Intent intent = getIntent();
        long lecture_id = intent.getLongExtra("lecture_id", -1);
        String lecture = intent.getStringExtra("lecture");

        TextView subjectNameView = findViewById(R.id.newPostSubjectNameView);
        subjectNameView.setText(lecture);

        EditText postTitleEditText = findViewById(R.id.postTitleEditText);
        EditText postContentEditText = findViewById(R.id.postContentEditText);

        Button postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(view -> {
            Pair<Long, String> credential = new Credential(NewPost.this).loadCredentials();
            Long user_id = credential.first;
            String token = credential.second;

            NewPostRequest data = new NewPostRequest();
            data.setUser_id(user_id);
            data.setLecture_id(lecture_id);
            data.setTitle(postTitleEditText.getText().toString());
            data.setContent(postContentEditText.getText().toString());
            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/post",
                    payload,
                    new RestAPICaller.ApiCallback<NewPostResponse>(
                            NewPost.this,
                            NewPostResponse.class,
                            res -> {
                                Intent pd_intent = new Intent(NewPost.this, PostDetails.class);

                                pd_intent.putExtra("lecture", lecture);
                                pd_intent.putExtra("PostId", res.getPost_id());
                                startActivity(pd_intent);
                                NewPost.this.finish();
                            }
                    ));
        });
    }
}
