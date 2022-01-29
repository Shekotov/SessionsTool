package ru.tigerframework.sessionstool;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

public enum HolidayTimeInterval {
    NIGHT1(1, 0, 10, 2),  // 01:00 - 10:02
    DAY(10, 2, 19, 30),   // 10:02 - 19:30
    NIGHT2(19, 30, 23, 0),// 19:30 - 23:00
    GAP(23, 0, 1, 0),     // 23:00 - 01:00
    ;

    private TimeInterval timeInterval;

    private HolidayTimeInterval(int hoursA, int minutesA, int hoursB, int minutesB) {
        this.timeInterval = new TimeInterval(hoursA, minutesA, hoursB, minutesB);
    }

    public static ZonedDateTime calculateCursorPoint(ZonedDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();
        LocalTime localTime = NIGHT1.timeInterval.getStart();
        ZonedDateTime startPoint = ZonedDateTime.of(localDate, localTime, dateTime.getZone());
        return startPoint.isAfter(dateTime) ? startPoint.minusDays(1) : startPoint;
    }

    public static long calculateCursorPointAsMillis(ZonedDateTime dateTime) {
        return zonedDateTimeToMillis(calculateCursorPoint(dateTime));
    }

    public static long zonedDateTimeToMillis(ZonedDateTime dateTime) {
        return dateTime.toInstant().toEpochMilli();
    }

    public HolidayTimeInterval next() {
        switch (this) {
            case NIGHT1: return DAY;
            case DAY:    return NIGHT2;
            case NIGHT2: return GAP;
            case GAP:    return NIGHT1;
            default:     throw new IllegalStateException();
        }
    }

    public ZonedDateTime nextCursorPoint(ZonedDateTime dateTime) {
        return dateTime.plus(timeInterval.getDuration());
    }

    public long nextCursorPoint(long millis) {
        return millis + timeInterval.getDuration().toMillis();
    }

    public LongRange toLongRange(ZonedDateTime dateTime) {
        return toLongRange(zonedDateTimeToMillis(dateTime));
    }

    public LongRange toLongRange(long millis) {
        return new LongRange(millis, millis + timeInterval.getDuration().toMillis());
    }

    public SessionType toSessionType() {
        switch (this) {
            case NIGHT1: return SessionType.NIGHT1_HOLIDAY;
            case DAY:    return SessionType.DAY_HOLIDAY;
            case NIGHT2: return SessionType.NIGHT2_HOLIDAY;
            default:     throw new IllegalStateException();
        }
    }
}
