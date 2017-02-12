package com.taobao.pamirs.schedule.test;


import org.junit.Test;
import org.unitils.UnitilsJUnit4;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByName;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

/**
 * µ÷¶È²âÊÔ
 * @author xuannan
 *
 */
@SpringApplicationContext( { "schedule.xml" })
public class StartDemoSchedule  extends UnitilsJUnit4{
	@SpringBeanByName
	TBScheduleManagerFactory scheduleManagerFactory;
	
    public void setScheduleManagerFactory(
			TBScheduleManagerFactory tbScheduleManagerFactory) {
		this.scheduleManagerFactory = tbScheduleManagerFactory;
	}
	@Test    
	public void testRunData() throws Exception {
		Thread.sleep(100000000000000L);
	}
}
