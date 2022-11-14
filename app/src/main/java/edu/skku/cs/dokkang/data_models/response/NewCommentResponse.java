package edu.skku.cs.dokkang.data_models.response;

public class NewCommentResponse {
    private String status;
    private Long comment_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }
}
