package ru.tigerframework.sessionstool;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HolidaySessionCreator {
    public List<Session> create(List<Session> tradeSessions, Date holidayStart, Date holidayEnd, TimeZone timeZone) {
        ZoneId zoneId = timeZone.toZoneId();
        return create(tradeSessions,
                      dateToZonedDateTime(holidayStart, zoneId),
                      dateToZonedDateTime(holidayStart, zoneId));
    }

    public List<Session> create(List<Session> tradeSessions, ZonedDateTime holidayStart, ZonedDateTime holidayEnd) {
        List<Session> holidaySessions = new ArrayList<>();
        ZoneId zoneId = holidayStart.getZone();
        long startPoint = HolidayTimeInterval.zonedDateTimeToMillis(holidayStart);
        long endPoint = HolidayTimeInterval.zonedDateTimeToMillis(holidayEnd);

        for (Session tradeSession : tradeSessions) {
            if (!isWeekend(tradeSession, zoneId)) {
                LongRange tradeRange = new LongRange(tradeSession.getStartDate().getTime(),
                                                     tradeSession.getEndDate().getTime());
                LongRange remainingRange = new LongRange(Math.max(tradeSession.getStartDate().getTime(), startPoint),
                                                         endPoint);

                if (LongRange.isOverlapped(tradeRange, remainingRange)) {
                    LongRange overlap = LongRange.getOverlap(tradeRange, remainingRange);
                    holidaySessions.add(createHolidaySession(overlap, tradeSession.getType().toHoliday()));
                }
            }
        }

        fixTimeOffset(holidayStart, holidayEnd, holidaySessions);

        return holidaySessions;
    }

    public static boolean isWeekend(Session session, ZoneId zoneId) {
        return isWeekend(session.getStartDate().getTime(), zoneId) &&
               isWeekend(session.getEndDate().getTime(), zoneId);
    }

    public static void fixTimeOffset(ZonedDateTime holidayStart, ZonedDateTime holidayEnd, List<Session> sessions) {
        for (Session session : sessions) {
            fixTimeOffset(holidayStart, holidayEnd, session);
        }
    }

    public static void fixTimeOffset(ZonedDateTime holidayStart, ZonedDateTime holidayEnd, Session session) {
        if (session.getType() == SessionType.NIGHT1_HOLIDAY) {
            session.setStartTimeOffset(calculateStartTimeOffset(holidayStart, session));
            return;
        }

        if (session.getType() == SessionType.DAY_HOLIDAY) return;

        if (session.getType() == SessionType.NIGHT2_HOLIDAY) {
            session.setEndTimeOffset(calculateEndTimeOffset(holidayEnd, session));
            return;
        }
    }

    public static int calculateStartTimeOffset(ZonedDateTime holidayStart, Session session) {
        ZonedDateTime sessionStart = dateToZonedDateTime(session.getStartDate(), holidayStart.getZone());
        ZonedDateTime sessionEnd = dateToZonedDateTime(session.getEndDate(), holidayStart.getZone());
        ZonedDateTime currentDateTime = null;

        if (sessionStart.getDayOfWeek() == DayOfWeek.MONDAY) currentDateTime = sessionStart;
        if (sessionEnd.getDayOfWeek() == DayOfWeek.MONDAY) currentDateTime = sessionEnd;

        if (currentDateTime == null) {
            return 0;
        } else {
            // Can be from -2 to 0;
            int offset = durationDays(holidayStart, currentDateTime) * (-1);
            return Math.max(offset, -2);
        }
    }

    public static int calculateEndTimeOffset(ZonedDateTime holidayEnd, Session session) {
        ZonedDateTime sessionStart = dateToZonedDateTime(session.getStartDate(), holidayEnd.getZone());
        ZonedDateTime sessionEnd = dateToZonedDateTime(session.getEndDate(), holidayEnd.getZone());
        ZonedDateTime currentDateTime = null;

        if (sessionStart.getDayOfWeek() == DayOfWeek.FRIDAY) currentDateTime = sessionStart;
        if (sessionEnd.getDayOfWeek() == DayOfWeek.FRIDAY) currentDateTime = sessionEnd;

        if (currentDateTime == null) {
            return 0;
        } else {
            // Can be from 0 to +2;
            int offset = durationDays(currentDateTime, holidayEnd);
            return Math.min(offset, 2);
        }
    }

    public static int durationDays(ZonedDateTime dateTimeA, ZonedDateTime dateTimeB) {
        return (int) Duration.between(dateTimeA.truncatedTo(ChronoUnit.DAYS),
                        dateTimeB.truncatedTo(ChronoUnit.DAYS))
                .toDays();
    }

    public static DayOfWeek getDayOfWeek(long millis, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId).getDayOfWeek();
    }

    public static boolean isWeekend(long millis, ZoneId zoneId) {
        return isWeekend(getDayOfWeek(millis, zoneId));
    }

    public static boolean isWeekend(DayOfWeek day) {
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static Session createHolidaySession(LongRange range, SessionType sessionType) {
        return new Session(range.toDateRange(), sessionType);
    }

    public static ZonedDateTime dateToZonedDateTime(Date date, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
    }
}
