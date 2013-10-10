package com.rquinn.darts;

import org.junit.Assert;
import org.junit.Test;

public class PageDataTest {
	

	  @Test
	  public void testGetDomain()
	  {
		PageData pageData = new PageData();		
	    Assert.assertEquals(pageData.getDomain(), "localhost:8080");
	  }

    @Test
    public void testGetHttpsToggle()
    {
        PageData pageData = new PageData();
        Assert.assertEquals(pageData.getHttpsToggle(), "http");
    }

}
