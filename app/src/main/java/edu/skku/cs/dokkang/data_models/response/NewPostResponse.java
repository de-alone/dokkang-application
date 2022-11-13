package edu.skku.cs.dokkang.data_models.response;

public class NewPostResponse {
    private String status;
    private Long post_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }
}
