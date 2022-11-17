package edu.skku.cs.dokkang.data_models.response;

public class NewStudyGroupResponse {
    private String status;
    private long studygroup_id;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getStudygroup_id() {
        return studygroup_id;
    }

    public void setStudygroup_id(long studygroup_id) {
        this.studygroup_id = studygroup_id;
    }
}
