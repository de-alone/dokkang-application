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
import java.util.List;

/*lecture information*/
class MySubject {
    private String name;
    private String professor;
    private int id;
    private String no;
    private boolean checked = false;

    public String getName() {
        return name;
    }

    public String getProfessor() {
        return professor;
    }

    public int getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public boolean getChecked() {return checked; }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setChecked(boolean checked) {this.checked = checked; }
}


public class MySubjectListViewAdapter extends BaseAdapter {

    private List<MySubject> item;
    private Context mContext;


    public MySubjectListViewAdapter(List<MySubject> item, Context mContext) {
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
        lecture_name.setText(item.get(i).getName());

        /* go to the community activity*/
        community_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), CommunityMain.class );
            intent.putExtra("lecture", item.get(i).getName());
            intent.putExtra("professor", item.get(i).getProfessor());
            intent.putExtra("lecture_id", item.get(i).getId());
            intent.putExtra("lecture_no", item.get(i).getNo());
            viewGroup.getContext().startActivity(intent);
        });

        /* go to the study group activity*/
        study_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), StudyGroupMain.class );
            intent.putExtra("lecture", item.get(i).getName());
            intent.putExtra("professor", item.get(i).getProfessor());
            intent.putExtra("lecture_id", item.get(i).getId());
            intent.putExtra("lecture_no", item.get(i).getNo());
            viewGroup.getContext().startActivity(intent);
        });

        return view;
    }
}
