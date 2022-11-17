package edu.skku.cs.dokkang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.data_models.LecturePost;
import edu.skku.cs.dokkang.data_models.StudyGroupPost;

public class StudyGroupPostListViewAdapter extends BaseAdapter {
    private List<StudyGroupPost> item;
    private final Context mContext;

    public StudyGroupPostListViewAdapter(List<StudyGroupPost> item, Context mContext) {
        this.item = item;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    public void addItems(List<StudyGroupPost> newItem) {
        List<StudyGroupPost> item = new ArrayList<>();
        item.addAll(this.item);
        item.addAll(newItem);
        this.item = item;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.studygrouplist, viewGroup, false);
        }

        TextView state = view.findViewById(R.id.stateTextView);
        TextView postTitle = view.findViewById(R.id.postTitleView);
        ImageView like = view.findViewById(R.id.likeImage);
        TextView likedNum = view.findViewById(R.id.likedNum);
        ImageView comment = view.findViewById(R.id.commentImage);
        TextView commentNum = view.findViewById(R.id.commentNum);

        if (item.get(i).isOpened()) {
            state.setText("Open");
        } else {
            state.setText("Closed");
        }

        postTitle.setText(item.get(i).getTitle());
        like.setImageResource(R.drawable.like);
        likedNum.setText(item.get(i).getNum_likes() + "");
        comment.setImageResource(R.drawable.comment);
        commentNum.setText(item.get(i).getNum_comments() + "");

        return view;
    }
}
