package com.CrmScheduler.entity;

import org.springframework.lang.NonNull;

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
    private int timeElapsed;

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

    public int getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(int timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public DbLog(int logId, String methodSignature, String wasSuccessful, int timeElapsed) {
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