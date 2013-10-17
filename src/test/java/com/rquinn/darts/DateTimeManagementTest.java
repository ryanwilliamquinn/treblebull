package com.rquinn.darts;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class DateTimeManagementTest {


    @Test
    public void testIsTimestampWithinOneHourOfCurrentTime()
    {
        DateTimeManagement dtm = new DateTimeManagement();
        long oneHour = 1000*60*60;
        DateTime twoHoursAgo = new DateTime(DateTime.now().getMillis() - (oneHour * 2));
        dtm.setTimestamp(new Timestamp(twoHoursAgo.getMillis()));
        Assert.assertFalse(dtm.isTimestampWithinOneHourOfCurrentTime());

        DateTime oneHourAgo = new DateTime(DateTime.now().getMillis() - oneHour);
        dtm.setTimestamp(new Timestamp(oneHourAgo.getMillis()));
        Assert.assertTrue(dtm.isTimestampWithinOneHourOfCurrentTime());

        DateTime halfAnHourAgo = new DateTime(DateTime.now().getMillis() - (oneHour / 2));
        dtm.setTimestamp(new Timestamp(halfAnHourAgo.getMillis()));
        Assert.assertTrue(dtm.isTimestampWithinOneHourOfCurrentTime());

        DateTime justPastOneHour = new DateTime(DateTime.now().getMillis() - (oneHour + 1));
        dtm.setTimestamp(new Timestamp(justPastOneHour.getMillis()));
        Assert.assertFalse(dtm.isTimestampWithinOneHourOfCurrentTime());

    }

}
