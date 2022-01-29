package ru.tigerframework.sessionstool;

import java.util.Date;
import java.util.Objects;

public class LongRange {
    private long start;
    private long end;

    public LongRange(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public LongRange(LongRange that) {
        this.start = that.start;
        this.end = that.end;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public static boolean isOverlapped(LongRange a, LongRange b) {
        return inside(a, b) ||
               cover(a, b) ||
               overlapAtBegin(a, b) ||
               overlapAtEnd(a, b);
    }

    // a   --
    // b ------
    public static boolean inside(LongRange a, LongRange b) {
        return a.start >= b.start && a.end <= b.end;
    }

    // a ------
    // b   --
    public static boolean cover(LongRange a, LongRange b) {
        return a.start <= b.start && a.end >= b.end;
    }

    // a ----
    // b   ----
    public static boolean overlapAtBegin(LongRange a, LongRange b) {
        return a.start <= b.start && a.end >= b.start && a.end <= b.end;
    }

    // a   ----
    // b ----
    public static boolean overlapAtEnd(LongRange a, LongRange b) {
        return a.start >= b.start && a.start <= b.end && a.end >= b.end;
    }

    public boolean isOverlapped(LongRange that) {
        return isOverlapped(this, that);
    }

    public static LongRange getOverlap(LongRange a, LongRange b) {
        if (inside(a, b)) return new LongRange(a);
        if (cover(a, b)) return new LongRange(b);
        if (overlapAtBegin(a, b)) return new LongRange(b.start, a.end);
        if (overlapAtEnd(a, b)) return new LongRange(a.start, b.end);
        throw new IllegalArgumentException("No overlap! a = " + a + ", b = " + b);
    }

    public LongRange getOverlap(LongRange that) {
        return getOverlap(this, that);
    }

    public DateRange toDateRange() {
        return new DateRange(new Date(start), new Date(end));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongRange longRange = (LongRange) o;
        return start == longRange.start && end == longRange.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "LongRange{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
