package ru.tigerframework.sessionstool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

public class DateRangeTest {
    @Test
    public void testOverlap() {
        DateRange a = new DateRange(new Date(), new Date());
        DateRange b = new DateRange(new Date(), new Date());

        assertTrue(DateRange.isOverlapped(a, b), "DateRange overlap");
    }
}
