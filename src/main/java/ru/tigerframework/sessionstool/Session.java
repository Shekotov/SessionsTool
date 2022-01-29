package ru.tigerframework.sessionstool;

import java.util.Date;

public class Session {
    private Date startDate;
    private Date endDate;
    private SessionType type;
    private int startTimeOffset = 0;
    private int endTimeOffset = 0;

    public Session(Date startDate, Date endDate, SessionType type) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
    }

    public Session(DateRange range, SessionType type) {
        this(range.getStart(), range.getEnd(), type);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public SessionType getType() {
        return type;
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    public int getStartTimeOffset() {
        return startTimeOffset;
    }

    public void setStartTimeOffset(int startTimeOffset) {
        this.startTimeOffset = startTimeOffset;
    }

    public int getEndTimeOffset() {
        return endTimeOffset;
    }

    public void setEndTimeOffset(int endTimeOffset) {
        this.endTimeOffset = endTimeOffset;
    }

    @Override
    public String toString() {
        return "Session{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", type=" + type +
                ", startTimeOffset=" + startTimeOffset +
                ", endTimeOffset=" + endTimeOffset +
                '}';
    }
}
