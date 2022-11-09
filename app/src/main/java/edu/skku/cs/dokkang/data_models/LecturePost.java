package edu.skku.cs.dokkang.data_models;

public class LecturePost {
    private long id;
    private long lecture_id;
    private String title;
    private int num_likes;
    private int num_comments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(long lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }
}
