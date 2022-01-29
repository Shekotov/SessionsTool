package ru.tigerframework.sessionstool;

public enum SessionType {
    NIGHT1,
    DAY,
    NIGHT2,
    NIGHT1_HOLIDAY,
    DAY_HOLIDAY,
    NIGHT2_HOLIDAY,
    ;

    public boolean isNight1() {
        return this == NIGHT1 ||
               this == NIGHT1_HOLIDAY;
    }

    public boolean isDay() {
        return this == DAY ||
               this == DAY_HOLIDAY;
    }

    public boolean isNight2() {
        return this == NIGHT2 ||
               this == NIGHT2_HOLIDAY;
    }

    public boolean isHoliday() {
        return this == NIGHT1_HOLIDAY ||
               this == DAY_HOLIDAY ||
               this == NIGHT2_HOLIDAY;
    }

    public boolean isNotHoliday() {
        return !isHoliday();
    }
}
