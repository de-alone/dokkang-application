package edu.skku.cs.dokkang.data_models.response;

import java.util.Optional;
import java.util.List;

import edu.skku.cs.dokkang.data_models.LecturePost;

public class PostListResponse {
    private String status;
    private List<LecturePost> posts;
    private String before;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LecturePost> getPosts() {
        return posts;
    }

    public void setPosts(List<LecturePost> posts) {
        this.posts = posts;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

}
