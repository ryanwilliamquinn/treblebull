package com.rquinn.darts;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/10/12
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DartsResultServiceTest
{
    private static DartsResultService dartsResultService;

    @BeforeClass
    public static void setup()
    {
        dartsResultService = new DartsResultService();
    }

    @AfterClass
    public static void teardown()
    {
        dartsResultService = null;
    }

    @Test
    public void testGetResultById()
    {
        DartsResult dartsResult = dartsResultService.getResultById(185);
        Assert.assertNotNull(dartsResult);
    }
}
