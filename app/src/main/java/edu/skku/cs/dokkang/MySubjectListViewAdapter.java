package edu.skku.cs.dokkang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/*lecture infomation*/
class MySubject {
    public String lecture_name;
    public String prefessor_name;
    public int lecture_id;
    public int lecture_no;


    public MySubject(String lecture_name, String prefessor_name, int lecture_id, int lecture_no) {
        this.lecture_name = lecture_name;
        this.prefessor_name = prefessor_name;
        this.lecture_id = lecture_id;
        this.lecture_no = lecture_no;
    }
}


public class MySubjectListViewAdapter extends BaseAdapter {

    private ArrayList<MySubject> item;
    private Context mContext;


    public MySubjectListViewAdapter(ArrayList<MySubject> item, Context mContext) {
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

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.subjectlist, viewGroup, false);
        }

        TextView lecture_name = view.findViewById(R.id.subjectNametextView);
        Button community_btn = view.findViewById(R.id.communitybutton);
        Button study_btn = view.findViewById(R.id.studybutton);

        /* set Textview as lecture name */
        lecture_name.setText(item.get(i).lecture_name);

        /* go to the community activity*/
        community_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), CommunityMain.class );
            intent.putExtra("lecture", item.get(i).lecture_name);
            intent.putExtra("professor", item.get(i).prefessor_name);
            intent.putExtra("lecture_id", item.get(i).lecture_id);
            intent.putExtra("lecture_no", item.get(i).lecture_no);
            viewGroup.getContext().startActivity(intent);
        });

        /* go to the study group activity*/
        study_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), StudyGroupMain.class );
            intent.putExtra("lecture", item.get(i).lecture_name);
            intent.putExtra("professor", item.get(i).prefessor_name);
            intent.putExtra("lecture_id", item.get(i).lecture_id);
            intent.putExtra("lecture_no", item.get(i).lecture_no);
            viewGroup.getContext().startActivity(intent);
        });



        return view;
    }
}
