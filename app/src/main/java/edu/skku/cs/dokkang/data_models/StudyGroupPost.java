package edu.skku.cs.dokkang.data_models;

import android.os.Parcelable;

import java.io.Serializable;

public class StudyGroupPost implements Serializable {
    private long id;
    private long lecture_id;
    private String title;
    private String content;
    private int num_likes;
    private int num_comments;
    private int num_participant;
    private int num_total;
    private String date;
    private String place;

    public StudyGroupPost(String title, int num_likes, int num_comments, int num_participant, int num_total, String date, String place) {
        this.title = title;
        this.num_likes = num_likes;
        this.num_comments = num_comments;
        this.num_participant = num_participant;
        this.num_total = num_total;
        this.date = date;
        this.place = place;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getNum_participant() {
        return num_participant;
    }

    public void setNum_participant(int num_participant) {
        this.num_participant = num_participant;
    }

    public int getNum_total() {
        return num_total;
    }

    public void setNum_total(int num_total) {
        this.num_total = num_total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
