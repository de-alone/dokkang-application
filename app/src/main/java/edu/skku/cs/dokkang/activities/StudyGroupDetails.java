package edu.skku.cs.dokkang.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.data_models.StudyGroupPost;

public class StudyGroupDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studygroup_details);

        Intent intent = getIntent();
        StudyGroupPost post = (StudyGroupPost) intent.getSerializableExtra("studygroup");
        String lecture = intent.getStringExtra("lecture");

        TextView sg_lecture = findViewById(R.id.sgtitle);
        sg_lecture.setText(lecture);

        TextView title = findViewById(R.id.sg_title_textView);
        TextView content = findViewById(R.id.sg_content_textView);
        TextView member = findViewById(R.id.memberTextview);
        TextView date = findViewById(R.id.dateTextview);
        TextView place = findViewById(R.id.placeTextview);
        TextView like = findViewById(R.id.Liketextview);
        TextView comment = findViewById(R.id.sg_commenttextview);

        title.setText(post.getTitle());
        content.setText(post.getContent());
        member.setText("Member: " + post.getNum_participant() + "/" + post.getNum_total());
        date.setText(post.getDate());
        place.setText(post.getPlace());
        like.setText("Like: " + post.getNum_likes());
        comment.setText("Comments: " + post.getNum_comments());
    }
}