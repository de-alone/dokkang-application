package edu.skku.cs.dokkang.data_models.request;

public class NewStudyGroupRequest {
    private long lecture_id;
    private long user_id;
    private String title;
    private String content;
    private int studycapacity;
    private String studytime;
    private String studyplace;

    public long getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(long lecture_id) {
        this.lecture_id = lecture_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStudycapacity() {
        return studycapacity;
    }

    public void setStudycapacity(int studycapacity) {
        this.studycapacity = studycapacity;
    }

    public String getStudytime() {
        return studytime;
    }

    public void setStudytime(String studytime) {
        this.studytime = studytime;
    }

    public String getStudyplace() {
        return studyplace;
    }

    public void setStudyplace(String studyplace) {
        this.studyplace = studyplace;
    }
}
