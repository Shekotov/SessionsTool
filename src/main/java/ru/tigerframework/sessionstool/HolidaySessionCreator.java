package ru.tigerframework.sessionstool;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class HolidaySessionCreator {
    public List<Session> create(Date holidayStart, Date holidayEnd, TimeZone timeZone) {
        ZoneId zoneId = timeZone.toZoneId();
        return create(dateToZonedDateTime(holidayStart, zoneId),
                      dateToZonedDateTime(holidayStart, zoneId));
    }

    public List<Session> create(ZonedDateTime holidayStart, ZonedDateTime holidayEnd) {
        List<Session> sessions = new ArrayList<>();
        long startPoint = HolidayTimeInterval.zonedDateTimeToMillis(holidayStart);
        long endPoint = HolidayTimeInterval.zonedDateTimeToMillis(holidayEnd);
        long cursorPoint = HolidayTimeInterval.calculateCursorPointAsMillis(holidayStart);
        HolidayTimeInterval timeInterval = HolidayTimeInterval.NIGHT1;

        while (cursorPoint <= endPoint) {
            if (timeInterval != HolidayTimeInterval.GAP) {
                LongRange currentRange = timeInterval.toLongRange(cursorPoint);
                LongRange remainingRange = new LongRange(Math.max(cursorPoint, startPoint), endPoint);

                if (LongRange.isOverlapped(currentRange, remainingRange)) {
                    LongRange overlap = LongRange.getOverlap(currentRange, remainingRange);
                    sessions.add(createSession(overlap, timeInterval));
                }
            }

            cursorPoint = timeInterval.nextCursorPoint(cursorPoint);
            timeInterval = timeInterval.next();
        }

        return sessions;
    }

    public static Session createSession(LongRange range, HolidayTimeInterval timeInterval) {
        return new Session(range.toDateRange(), timeInterval.toSessionType());
    }

    public static ZonedDateTime dateToZonedDateTime(Date date, ZoneId zoneId) {
        return ZonedDateTime.ofInstant(date.toInstant(), zoneId);
    }
}
