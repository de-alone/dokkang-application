package edu.skku.cs.dokkang.data_models.response;

import java.util.List;

import edu.skku.cs.dokkang.data_models.Comment;

public class StudyGroupDetailResponse {
    private String status;
    private long id;
    private long lecture_id;
    private long user_id;
    private String username;
    private String title;
    private String content;
    private String created_at;
    private int num_likes;
    private List<Comment> comments;
    private List<String> participants;
    private String studytime;
    private String studyplace;
    private int studycapacity;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getNum_likes() {
        return num_likes;
    }

    public void setNum_likes(int num_likes) {
        this.num_likes = num_likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
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

    public int getStudycapacity() {
        return studycapacity;
    }

    public void setStudycapacity(int studycapacity) {
        this.studycapacity = studycapacity;
    }
}
