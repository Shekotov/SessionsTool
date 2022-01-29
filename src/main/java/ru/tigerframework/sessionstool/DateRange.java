package ru.tigerframework.sessionstool;

import java.util.Date;
import java.util.Objects;

public class DateRange {
    private Date start;
    private Date end;

    public DateRange(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public static boolean isOverlapped(DateRange a, DateRange b) {
        return LongRange.isOverlapped(a.toLongRange(), b.toLongRange());
    }

    public boolean isOverlapped(DateRange that) {
        return isOverlapped(this, that);
    }

    public static DateRange getOverlap(DateRange a, DateRange b) {
        return LongRange.getOverlap(a.toLongRange(), b.toLongRange()).toDateRange();
    }

    public DateRange getOverlap(DateRange that) {
        return getOverlap(this, that);
    }

    public LongRange toLongRange() {
        return new LongRange(start.getTime(), end.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateRange dateRange = (DateRange) o;
        return start.equals(dateRange.start) && end.equals(dateRange.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "ru.tigerframework.sessionstool.DateRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
