package ru.tigerframework.sessionstool;

import java.time.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HolidaySessionCreator creator = new HolidaySessionCreator();

        LocalDate startDate = LocalDate.of(2022, 1, 29);
        LocalTime startTime = LocalTime.of(0, 0);
        LocalDate endDate = LocalDate.of(2022, 1, 30);
        LocalTime endTime = LocalTime.of(22, 30);
        ZoneId zoneId = ZoneId.systemDefault();

        List<Session> sessions = creator.create(ZonedDateTime.of(startDate, startTime, zoneId),
                                                ZonedDateTime.of(endDate, endTime, zoneId));

        for (Session session : sessions) {
            System.out.println(session);
        }
    }
}
