package edu.skku.cs.dokkang;

public class LoginResponseDataModel {
    private String status;
    private String token;
//    private long user_id;

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
}
