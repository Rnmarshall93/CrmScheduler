package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.entity.DbLog;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class DbDaoImplSql implements IDbDao{
    @Override
    public void createdDbLog(DbLog dbLog) {
        Session session = HibernateConf.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(dbLog);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public DbLog getDbLog(long dbLogId) {
        Session session = HibernateConf.getSessionFactory().openSession();
        session.beginTransaction();

        DbLog foundDbLog = session.get(DbLog.class, dbLogId);

        session.getTransaction().commit();
        session.close();

        return foundDbLog;
    }

    @Override
    public ArrayList<DbLog> getAllDbLogs() {
        Session session = HibernateConf.getSessionFactory().openSession();
        session.beginTransaction();

        ArrayList<DbLog> allDbLogs = new ArrayList<>();
        Query query = session.createQuery("from DbLog");
        allDbLogs.addAll(query.list());

        session.getTransaction().commit();
        session.close();
        return allDbLogs;
    }
}