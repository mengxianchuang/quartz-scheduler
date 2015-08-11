/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	SchedulerConfigDao.java    
 * @Description:SchedulerConfigDao  
 * @author: 	Hoctor
 * @Creat: 		2015年8月10日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月10日		Hoctor		
 */
package com.touyun.scheduler.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.touyun.scheduler.model.SchedulerConfig;

/**
 * @ClassName: SchedulerConfigDao
 */

@Component("schedulerConfigDao")
public class SchedulerConfigDao extends GenericDao {

    private static final String insertSql = "insert into scheduler_config(GroupName,JobName,JobClass,JobTrigger,GroupType,"
            + "Status,Remark,CreateUser,CreateTime,EditUser,EditTime,LatestSuccessFulRunTime) values(?,?,?,?,?,?,?,?,?,?,?,?)";

    public boolean insert(SchedulerConfig schedulerConfig) {
        Object[] args = { schedulerConfig.getGroupName(), schedulerConfig.getJobName(), schedulerConfig.getJobClass(),
                schedulerConfig.getJobTrigger(), schedulerConfig.getGroupType(), schedulerConfig.getStatus(),
                schedulerConfig.getRemark(), schedulerConfig.getCreateUser(), schedulerConfig.getCreateTime(),
                schedulerConfig.getEditUser(), schedulerConfig.getEditTime(), schedulerConfig.getLatestSuccessFulRunTime() };
        int rowsAffected = this.getJdbcTemplate().update(insertSql, args);
        if (rowsAffected == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean updateStatus(SchedulerConfig schedulerConfig) {
        String sql = "update scheduler_config set Status= 1 where GroupName=? and JobName=?";
        Object[] args = { schedulerConfig.getGroupName(), schedulerConfig.getJobName() };
        int rowsAffected = this.getJdbcTemplate().update(sql, args);
        if (rowsAffected == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<SchedulerConfig> findAll() {
        String sql = "SELECT * FROM scheduler_config";

        RowMapper<SchedulerConfig> rowMapper = new RowMapper<SchedulerConfig>() {

            @Override
            public SchedulerConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
                SchedulerConfig schedulerConfig = new SchedulerConfig();
                schedulerConfig.setGroupName(rs.getString("GroupName"));
                schedulerConfig.setJobName(rs.getString("JobName"));
                schedulerConfig.setJobClass(rs.getString("JobClass"));
                schedulerConfig.setJobTrigger(rs.getString("JobTrigger"));
                schedulerConfig.setGroupType(rs.getInt("GroupType"));
                schedulerConfig.setStatus(rs.getInt("Status"));
                schedulerConfig.setCreateUser(rs.getString("CreateUser"));
                return schedulerConfig;
            }
        };
        List<SchedulerConfig> list = this.getJdbcTemplate().query(sql, rowMapper);
        return list;
    }

    public List<SchedulerConfig> findAllByGroupType(int groupType) {
        String sql = "SELECT * FROM scheduler_config WHERE GroupType=?";

        Object[] args = { groupType };

        RowMapper<SchedulerConfig> rowMapper = new RowMapper<SchedulerConfig>() {

            @Override
            public SchedulerConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
                SchedulerConfig schedulerConfig = new SchedulerConfig();
                schedulerConfig.setGroupName(rs.getString("GroupName"));
                schedulerConfig.setJobName(rs.getString("JobName"));
                schedulerConfig.setJobClass(rs.getString("JobClass"));
                schedulerConfig.setJobTrigger(rs.getString("JobTrigger"));
                schedulerConfig.setGroupType(rs.getInt("GroupType"));
                schedulerConfig.setStatus(rs.getInt("Status"));
                schedulerConfig.setCreateUser(rs.getString("CreateUser"));
                return schedulerConfig;
            }
        };
        List<SchedulerConfig> list = this.getJdbcTemplate().query(sql, args, rowMapper);
        return list;
    }
}
