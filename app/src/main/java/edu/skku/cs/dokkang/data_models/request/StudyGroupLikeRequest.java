package edu.skku.cs.dokkang.data_models.request;

public class StudyGroupLikeRequest {
    private long studygroup_id;
    private long user_id;

    public long getStudygroup_id() {
        return studygroup_id;
    }

    public void setStudygroup_id(long studygroup_id) {
        this.studygroup_id = studygroup_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
