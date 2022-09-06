package com.CrmScheduler.aspects;
import com.CrmScheduler.DAO.DbDaoImplSql;
import com.CrmScheduler.entity.DbLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
@Aspect
public class HibernateLoggingAspect {

    @Around(value = "execution(public * com.CrmScheduler.DAO.*.*(..))")
    public Object logDbCall(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = Instant.now().toEpochMilli();

        Object result = proceedingJoinPoint.proceed();

        long endTime = Instant.now().toEpochMilli();
        long timeElapsed = endTime - startTime;

        System.out.println("Time elapsed for " + proceedingJoinPoint.getSignature().toLongString() + "(in Millisec) : " + timeElapsed);

        DbLog dbLog = new DbLog();
        dbLog.setMethodSignature(proceedingJoinPoint.getSignature().toLongString());
        if(result == null)
            dbLog.setWasSuccessful("false");
        else
            dbLog.setWasSuccessful("true");

        dbLog.setTimeElapsed(timeElapsed);
        DbDaoImplSql dbDaoImplSql = new DbDaoImplSql();
        dbDaoImplSql.createdDbLog(dbLog);
      return result;
    }





}