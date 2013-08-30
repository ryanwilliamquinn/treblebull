package com.rquinn.darts;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class DartsResult {
    private static final Logger slf4jLogger = LoggerFactory.getLogger(DartsResult.class);

    @Expose private int id;
    private BasePracticeType type;
    @Expose private int score;
    @Expose private int numRounds;

    @Expose private DateTimeManagement dateTimeManagement = new DateTimeManagement();

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DartsResult() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BasePracticeType getType() {
        return type;
    }

    public void setType(BasePracticeType type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    public String toString() {
        return "id: " + id + " type: " + type + " score: " + score + ", numrounds: " + numRounds;
    }

    public DateTimeManagement getDateTimeManagement() {
        return dateTimeManagement;
    }

}
