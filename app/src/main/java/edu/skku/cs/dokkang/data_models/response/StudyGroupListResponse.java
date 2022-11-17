package edu.skku.cs.dokkang.data_models.response;

import java.util.List;

import edu.skku.cs.dokkang.data_models.StudyGroupPost;

public class StudyGroupListResponse {
    private String status;
    private List<StudyGroupPost> studygroups;
    private String before;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StudyGroupPost> getStudygroups() {
        return studygroups;
    }

    public void setStudygroups(List<StudyGroupPost> studygroups) {
        this.studygroups = studygroups;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }
}
