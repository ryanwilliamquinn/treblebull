package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/18/13
 * Time: 8:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class SingleDartResult {
    private int id;
    private String dart;
    private String target;
    private int score;
    private DateTimeManagement dateTimeManagement = new DateTimeManagement();
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDart() {
        return dart;
    }

    public void setDart(String dart) {
        this.dart = dart;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DateTimeManagement getDateTimeManagement() {
        return dateTimeManagement;
    }

    public void setDateMilliseconds(long dateMilliseconds) {
        dateTimeManagement.setDateMilliseconds(dateMilliseconds);
    }

    public String toString() {
        return "Dart: " + dart + ", score: " + score + ", millis: " + dateTimeManagement.getDateMilliseconds();
    }
}
