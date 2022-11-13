package edu.skku.cs.dokkang.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.adapters.CommentListViewAdapter;
import edu.skku.cs.dokkang.data_models.response.PostDetailResponse;

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
        PostDetailResponse post = (PostDetailResponse) intent.getSerializableExtra("post");

        PostDetail_activity = PostDetails.this;

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
        listViewAdapter = new CommentListViewAdapter(post.getComments(), getApplicationContext());
        comment_listView.setAdapter(listViewAdapter);

        // 좋아요 버튼 구현
        Button like_btn = findViewById(R.id.post_likebutton);

        // 댓글 전송 버튼 구현
        Button send_btn = findViewById(R.id.post_send_button);
    }
}
