/**    
 * <p>Copyright (c) Shanghai TY Technology Co., Ltd. All Rights Reserved.</p>
 *
 * @FileName: 	GenericDao.java    
 * @Description:GenericDao  
 * @author: 	Hoctor
 * @Creat: 		2015年8月8日  
 *
 * Modification History:
 * Data			Author		Version		   Description
 * -------------------------------------------------------------
 * 2015年8月8日		Hoctor		
 */
package com.touyun.scheduler.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName: GenericDao
 */

public class GenericDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
}
