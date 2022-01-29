package ru.tigerframework.sessionstool;

import java.time.Duration;
import java.time.LocalTime;

public class TimeInterval {
    private LocalTime start;
    private LocalTime end;
    private Duration duration;

    public TimeInterval(int hoursA, int minutesA, int hoursB, int minutesB) {
        this(LocalTime.of(hoursA, minutesA), LocalTime.of(hoursB, minutesB));
    }
    public TimeInterval(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
        this.duration = between(start, end);
    }

    public static Duration between(LocalTime timeStart, LocalTime timeEnd) {
        Duration duration = Duration.between(timeStart, timeEnd);
        return duration.isNegative() ? duration.plusHours(24) : duration;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public Duration getDuration() {
        return duration;
    }
}
