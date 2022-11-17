package edu.skku.cs.dokkang.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import edu.skku.cs.dokkang.Constant;
import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.RestAPICaller;
import edu.skku.cs.dokkang.activities.CommunityMain;
import edu.skku.cs.dokkang.activities.MyPage;
import edu.skku.cs.dokkang.activities.PostDetails;
import edu.skku.cs.dokkang.data_models.Comment;
import edu.skku.cs.dokkang.data_models.response.PostDetailResponse;
import edu.skku.cs.dokkang.data_models.response.UserDetailResponse;
import edu.skku.cs.dokkang.utils.Credential;

public class CommentListViewAdapter extends BaseAdapter {

    private List<Comment> item;
    private final Context mContext;
    private Activity activity;

    public CommentListViewAdapter(List<Comment> item, Context mContext, Activity activity) {
        this.item = item;
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public int getCount() { return item.size(); }

    @Override
    public Object getItem(int i) { return item.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.commentlist, viewGroup, false);
        }

        TextView user = view.findViewById(R.id.comment_username);
        TextView time = view.findViewById(R.id.comment_time);
        TextView content = view.findViewById(R.id.comment_content);

        Pair<Long, String> credential = new Credential(mContext).loadCredentials();
        String token = credential.second;

        time.setText(item.get(i).getCreated_at());
        content.setText(item.get(i).getContent());

        new RestAPICaller(token).get(Constant.SERVER_BASE_URI + "/user/" + item.get(i).getUser_id(),
                new RestAPICaller.ApiCallback<UserDetailResponse>(
                        activity,
                        UserDetailResponse.class,
                        data -> {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    user.setText(data.getUsername());
                                }
                            });
                        }
                )
        );

        return view;
    }
}
