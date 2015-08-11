/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	SchedulerConfig.java    
 * @Description:SchedulerConfig  
 * @author: 	Hoctor
 * @Creat: 		2015年8月10日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月10日		Hoctor		
 */
package com.touyun.scheduler.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SchedulerConfig
 */
public class SchedulerConfig implements Serializable {
    // TODO need implement Comparable<SchedulerConfig>
    private static final long serialVersionUID = -1747705996147271926L;

    private String groupName;

    private String jobName;

    private String jobClass;

    private String jobTrigger;

    private int groupType;

    private int status;

    private String remark;

    private String createUser;

    private Date createTime;

    private String editUser;

    private Date editTime;

    private Date latestSuccessFulRunTime;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobTrigger() {
        return jobTrigger;
    }

    public void setJobTrigger(String jobTrigger) {
        this.jobTrigger = jobTrigger;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getLatestSuccessFulRunTime() {
        return latestSuccessFulRunTime;
    }

    public void setLatestSuccessFulRunTime(Date latestSuccessFulRunTime) {
        this.latestSuccessFulRunTime = latestSuccessFulRunTime;
    }
}
