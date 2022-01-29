package ru.tigerframework.sessionstool;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HolidaySessionCreator creator = new HolidaySessionCreator();

        String holidayStart = "2022-01-28T01:00";
        String holidayEnd   = "2022-01-31T00:00";
        ZoneId zoneId = ZoneId.systemDefault();

        List<Session> tradeSessions = new ArrayList<>();
        tradeSessions.add(createSession("2022-01-27T23:00", "2022-01-28T10:02", zoneId, SessionType.NIGHT1));
        tradeSessions.add(createSession("2022-01-28T10:02", "2022-01-28T19:30", zoneId, SessionType.DAY));
        tradeSessions.add(createSession("2022-01-28T19:30", "2022-01-28T23:00", zoneId, SessionType.NIGHT2));
        tradeSessions.add(createSession("2022-01-30T23:00", "2022-01-31T10:02", zoneId, SessionType.NIGHT1));

        List<Session> holidaySessions = creator.create(tradeSessions, ZonedDateTime.of(LocalDateTime.parse(holidayStart), zoneId),
                                                                      ZonedDateTime.of(LocalDateTime.parse(holidayEnd), zoneId));

        for (Session session : holidaySessions) {
            System.out.println(session);
        }
    }

    public static Session createSession(String sessionStart, String sessionEnd,
                                        ZoneId zoneId, SessionType sessionType) {
        return new Session(Date.from(ZonedDateTime.of(LocalDateTime.parse(sessionStart), zoneId).toInstant()),
                           Date.from(ZonedDateTime.of(LocalDateTime.parse(sessionEnd), zoneId).toInstant()),
                           sessionType);
    }
}
