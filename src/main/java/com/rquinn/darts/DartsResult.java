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
    private Timestamp timestamp;
    @Expose private String displayDateTime;
    private DateTime dateTime;
    private String username;
    // date millis used for sorting on the front end
    @Expose private long dateMilliseconds;

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
        return "id: " + id + " type: " + type + " score: " + score;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        dateTime = StringUtils.substringBeforeLast(dateTime, ".");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");
        this.dateTime = DateTime.parse(dateTime, fmt);
        DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss");
        this.timestamp = new Timestamp(this.dateTime.getMillis());
        DateTimeFormatter fmt3 = DateTimeFormat.forPattern("MMM dd, yyyy");
        displayDateTime = fmt3.print(this.dateTime);
    }

    public String getDisplayDateTime() {
        if (displayDateTime == null) {
            DateTimeFormatter fmt2 = DateTimeFormat.forPattern("MMM dd, yyyy");
            displayDateTime = fmt2.print(dateTime);
        }
        return displayDateTime;
    }

    public void setDisplayDateTime(String dipslayDateTime) {
        this.displayDateTime = dipslayDateTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * need to make this set all of the times
     * @param timestamp
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        initializeDates();
    }

    public long getDateMilliseconds() {
        return dateMilliseconds;
    }

    public void setDateMilliseconds(long dateMilliseconds) {
        this.dateMilliseconds = dateMilliseconds;
    }

    public void initializeDates() {
        if (timestamp == null) {
            timestamp = new Timestamp(DateTime.now().getMillis());
        }
        DateTime date = new DateTime(timestamp);
        dateMilliseconds = date.getMillis();
        DateTimeFormatter fmt3 = DateTimeFormat.forPattern("MMM dd, yyyy");
        displayDateTime = fmt3.print(date);
    }


}
