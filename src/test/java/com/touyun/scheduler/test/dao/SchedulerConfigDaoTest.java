/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	SchedulerConfigDaoTest.java    
 * @Description:SchedulerConfigDaoTest  
 * @author: 	Hoctor
 * @Creat: 		2015年8月10日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月10日		Hoctor		
 */
package com.touyun.scheduler.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.touyun.scheduler.dao.SchedulerConfigDao;
import com.touyun.scheduler.model.SchedulerConfig;

/**
 * @ClassName: SchedulerConfigDaoTest
 */
public class SchedulerConfigDaoTest extends GenericDaoTest {

    @Autowired
    private SchedulerConfigDao schedulerConfigDao;

    @Test
    public void insertTest() {
        try {
            SchedulerConfig schedulerConfig = new SchedulerConfig();
            schedulerConfig.setGroupName("TestGroupName");
            schedulerConfig.setJobName("TestJobName");
            schedulerConfig.setJobClass("TestJob");
            schedulerConfig.setJobTrigger("0 0/1 * * * ?");
            schedulerConfig.setGroupType(0);
            schedulerConfig.setStatus(0);
            schedulerConfig.setRemark(null);
            schedulerConfig.setCreateUser("Hoctor");
            schedulerConfig.setCreateTime(null);
            schedulerConfig.setEditUser(null);
            schedulerConfig.setEditTime(null);
            schedulerConfig.setLatestSuccessFulRunTime(null);
            boolean result = schedulerConfigDao.insert(schedulerConfig);
            Assert.assertTrue(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findAllTest() {
        System.out.println(JSON.toJSONString(schedulerConfigDao.findAll()));
    }

    @Test
    public void findAllTestByGroupType() {
        try {
            System.out.println(JSON.toJSONString(schedulerConfigDao.findAllByGroupType(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
