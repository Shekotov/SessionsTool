package ru.tigerframework.sessionstool;

public enum SessionType {
    // holidays
    DAY_HOLIDAY,
    NIGHT1_HOLIDAY,
    NIGHT2_HOLIDAY,

    // regular hours
    DAY,
    NIGHT1,
    NIGHT2,
    ;

    public static SessionType parseOrNull(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            // ignore
        }

        return null;
    }

    public static SessionType parseOrException(String value) {
        return valueOf(value);
    }

    public SessionType toHoliday() {
        switch (this) {
            case NIGHT1: return NIGHT1_HOLIDAY;
            case DAY:    return DAY_HOLIDAY;
            case NIGHT2: return NIGHT2_HOLIDAY;
            default:     throw new IllegalStateException();
        }
    }
}