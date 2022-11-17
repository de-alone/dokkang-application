package edu.skku.cs.dokkang.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.data_models.request.NewStudyGroupRequest;
import edu.skku.cs.dokkang.data_models.response.NewStudyGroupResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class StudyGroupNewPost extends AppCompatActivity {

    public static Activity StudyGroupNewPost_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studygroup_new_post);

        Intent intent = getIntent();
        long lecture_id = intent.getLongExtra("lecture_id", -1);
        String lecture = intent.getStringExtra("lecture");

        TextView subjectNameView = findViewById(R.id.sg_newPostSubjectNameView);
        subjectNameView.setText(lecture);

        StudyGroupNewPost_activity = StudyGroupNewPost.this;

        EditText postTitleEditText = findViewById(R.id.sg_postTitleEditText);
        EditText postContentEditText = findViewById(R.id.sg_postContentEditText);
        EditText postNumEditText = findViewById(R.id.postNumEditText);
        EditText dateEditText = findViewById(R.id.dateEditText);
        EditText placeEditText = findViewById(R.id.placeEditText);

        Button postButton = findViewById(R.id.sg_postButton);

        postButton.setOnClickListener(view -> {
            Pair<Long, String> credential = new Credential(StudyGroupNewPost_activity).loadCredentials();
            Long user_id = credential.first;
            String token = credential.second;

            String arr[] = {postTitleEditText.getText().toString(), postContentEditText.getText().toString(),
                    postNumEditText.getText().toString(), dateEditText.getText().toString(), placeEditText.getText().toString()};
            for (String input : arr) {
                if (input.equals("")) {
                    Toast.makeText(StudyGroupNewPost_activity, "Please fill in the blanks.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            NewStudyGroupRequest data = new NewStudyGroupRequest();
            data.setUser_id(user_id);
            data.setLecture_id(lecture_id);
            data.setTitle(postTitleEditText.getText().toString());
            data.setContent(postContentEditText.getText().toString());
            data.setStudycapacity(Integer.parseInt(postNumEditText.getText().toString()));
            data.setStudytime(dateEditText.getText().toString());
            data.setStudyplace(placeEditText.getText().toString());

            String payload = new Gson().toJson(data);

            new RestAPICaller(token).post(Constant.SERVER_BASE_URI + "/studygroup",
                    payload,
                    new RestAPICaller.ApiCallback<NewStudyGroupResponse>(
                            StudyGroupNewPost_activity,
                            NewStudyGroupResponse.class,
                            res -> {
                                Intent sg_intent = new Intent(StudyGroupNewPost_activity, StudyGroupDetails.class);
                                sg_intent.putExtra("lecture", lecture);
                                sg_intent.putExtra("PostId", res.getStudygroup_id());
                                startActivity(sg_intent);
                                StudyGroupNewPost_activity.finish();
                            }
                    ));
        });
    }
}