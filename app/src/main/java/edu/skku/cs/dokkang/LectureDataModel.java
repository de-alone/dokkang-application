package edu.skku.cs.dokkang;

import java.util.List;

public class LectureDataModel {
    private String status;
    private List<MySubject> lectures;

    public String getStatus() {
        return status;
    }

    public void SetStatus(String status) {
        this.status = status;
    }

    public List<MySubject> getLectures() {
        return lectures;
    }

    public void SetLectures(List<MySubject> lectures) {
        this.lectures = lectures;
    }


}
