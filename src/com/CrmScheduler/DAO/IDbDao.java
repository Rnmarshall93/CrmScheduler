package com.CrmScheduler.DAO;

import com.CrmScheduler.entity.DbLog;

import java.util.ArrayList;

public interface IDbDao {

    void createdDbLog(DbLog dbLog);

    DbLog getDbLog(long dbLogId);

    ArrayList<DbLog> getAllDbLogs();

}
