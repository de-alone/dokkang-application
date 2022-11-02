package edu.skku.cs.dokkang.data_models.response;

import java.util.List;

import edu.skku.cs.dokkang.data_models.MySubject;

public class LectureResponse {
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
