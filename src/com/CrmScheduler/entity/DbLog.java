package com.CrmScheduler.entity;

import javax.persistence.*;

@Entity
@Table(name = "dblogs")
public class DbLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogID")
    private int logId;
    @Column(name = "MethodSignature")
    private String methodSignature;
    @Column(name = "WasSuccessful")
    private String wasSuccessful;
    @Column(name = "TimeElapsed")
    private long timeElapsed;

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }

    public String getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(String wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public DbLog(int logId, String methodSignature, String wasSuccessful, long timeElapsed) {
        this.logId = logId;
        this.methodSignature = methodSignature;
        this.wasSuccessful = wasSuccessful;
        this.timeElapsed = timeElapsed;
    }

    public DbLog() {
    }

    @Override
    public String toString() {
        return "DbLog{" +
                "logId=" + logId +
                ", methodSignature='" + methodSignature + '\'' +
                ", wasSuccessful='" + wasSuccessful + '\'' +
                ", timeElapsed=" + timeElapsed +
                '}';
    }
}