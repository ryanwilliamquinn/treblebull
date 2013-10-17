package com.rquinn.darts;

import com.google.gson.annotations.Expose;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/19/13
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class DateTimeManagement {

    // timestamp is the way we transfer times between the database and the business layer
    private Timestamp timestamp;
    // String version for display
    @Expose private String displayDateTime;
    // date millis used for sorting on the front end
    @Expose private long dateMilliseconds;

    public DateTimeManagement() {};

    public DateTimeManagement(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayDateTime() {
        if (displayDateTime == null) {
            DateTime date = new DateTime(timestamp);
            DateTimeFormatter fmt2 = DateTimeFormat.forPattern("MMM dd, yyyy");
            displayDateTime = fmt2.print(date);
        }
        return displayDateTime;
    }

    public void setDisplayDateTime(String displayDateTime) {
        this.displayDateTime = displayDateTime;
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
        createDisplayDate();
    }

    public void initializeDatesWithEpochTime() {
        timestamp = new Timestamp(this.dateMilliseconds);
        createDisplayDate();
    }

    private void createDisplayDate() {
        // format the timestamp into a datetime, then into a String for display
        DateTime date = new DateTime(timestamp);
        dateMilliseconds = date.getMillis();
        DateTimeFormatter fmt3 = DateTimeFormat.forPattern("MMM dd, yyyy");
        displayDateTime = fmt3.print(date);
    }

    public boolean isTimestampWithinOneHourOfCurrentTime() {
        // current time
        long currentTimeMillis = DateTime.now().getMillis();
        // existing time = timestamp field
        // if currentTime is before timestamp + 1 hour = 1000 * 60, then we are good
        long resetTimeMillis = new DateTime(timestamp).getMillis();
        long difference = currentTimeMillis - resetTimeMillis;
        long maxDifference = 1000 * 60 * 60;  // this should be one hour in milliseconds
        return difference <= maxDifference;
    }
}
