package com.dapid.agent.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2017
 **/
public class JobDefinition {
    private UUID id;
    private String name;
    private String command;
    private String machine;
    private String runAs;
    private String userProfile;
    private String alarmIfFail;
    private Integer retryOnFailure;
    private String cronDate;
    private String condition;
    private String stdoutFile;
    private String stderrFile;
    private Integer maxRunTime;
    private Integer maxRetry;

    private String comment;

    public JobDefinition(UUID id, String name, String command, String machine, String alarmIfFail, Integer retryOnFailure, String cronDate, Integer maxRunTime, Integer maxRetry) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.machine = machine;
        this.alarmIfFail = alarmIfFail;
        this.retryOnFailure = retryOnFailure;
        this.cronDate = cronDate;
        this.maxRunTime = maxRunTime;
        this.maxRetry = maxRetry;
    }

    public JobDefinition(UUID id, String name, String command, String machine, String runAs, String userProfile, String alarmIfFail, Integer retryOnFailure, String cronDate, String condition, String stdoutFile, String stderrFile, Integer maxRunTime, Integer maxRetry, String comment) {
        this.id = id;
        this.name = name;
        this.command = command;
        this.machine = machine;
        this.runAs = runAs;
        this.userProfile = userProfile;
        this.alarmIfFail = alarmIfFail;
        this.retryOnFailure = retryOnFailure;
        this.cronDate = cronDate;
        this.condition = condition;
        this.stdoutFile = stdoutFile;
        this.stderrFile = stderrFile;
        this.maxRunTime = maxRunTime;
        this.maxRetry = maxRetry;
        this.comment = comment;
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    public Integer getMaxRunTime() {
        return maxRunTime;
    }

    public void setMaxRunTime(Integer maxRunTime) {
        this.maxRunTime = maxRunTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getRunAs() {
        return runAs;
    }

    public void setRunAs(String runAs) {
        this.runAs = runAs;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getAlarmIfFail() {
        return alarmIfFail;
    }

    public void setAlarmIfFail(String alarmIfFail) {
        this.alarmIfFail = alarmIfFail;
    }

    public Integer getRetryOnFailure() {
        return retryOnFailure;
    }

    public void setRetryOnFailure(Integer retryOnFailure) {
        this.retryOnFailure = retryOnFailure;
    }

    public String getCronDate() {
        return cronDate;
    }

    public void setCronDate(String cronDate) {
        this.cronDate = cronDate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStdoutFile() {
        return stdoutFile;
    }

    public void setStdoutFile(String stdoutFile) {
        this.stdoutFile = stdoutFile;
    }

    public String getStderrFile() {
        return stderrFile;
    }

    public void setStderrFile(String stderrFile) {
        this.stderrFile = stderrFile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        JobDefinition that = (JobDefinition) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("command", command)
                .append("machine", machine)
                .append("runAs", runAs)
                .append("userProfile", userProfile)
                .append("alarmIfFail", alarmIfFail)
                .append("retryOnFailure", retryOnFailure)
                .append("cronDate", cronDate)
                .append("condition", condition)
                .append("stdoutFile", stdoutFile)
                .append("stderrFile", stderrFile)
                .append("comment", comment)
                .toString();
    }
}
