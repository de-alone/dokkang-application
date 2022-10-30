package edu.skku.cs.dokkang;

public class LoginResponseDataModel {
    private String status;
    private long user_id;
    private String token;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getUser_id() { return user_id; }

    public void setUser_id(long user_id) { this.user_id = user_id; }
}
