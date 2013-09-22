package com.rquinn.darts;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * User: rquinn
 * Date: 9/21/13
 * Time: 10:46 PM
 */
public class TargetPracticeTypeTest {

  /*
  @BeforeClass
  public static void setup()
  {
  }

  @AfterClass
  public static void teardown()
  {
  }
  */

  @Test
  public void testIsAnalytics()
  {
    Assert.assertTrue(TargetPracticeType.BULLS.isAnalytics());
    Assert.assertFalse(TargetPracticeType.DOUBLE_EIGHTEEN.isAnalytics());
  }


}
