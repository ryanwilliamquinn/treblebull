package com.rquinn.darts.model;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 10/16/13
 * Time: 8:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class User {

    private String username;
    private String email;
    private String password;
    private String resetToken;
    private Timestamp resetTimestamp;

    public Timestamp getResetTimestamp() {
        return resetTimestamp;
    }

    public void setResetTimestamp(Timestamp resetTimestamp) {
        this.resetTimestamp = resetTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }


}
