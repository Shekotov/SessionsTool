package ru.tigerframework.sessionstool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongRangeTest {
    @Test
    public void testNoOverlap() {
        LongRange a = new LongRange(1000, 2000);
        LongRange b = new LongRange(3000, 4000);

        assertFalse(LongRange.isOverlapped(a, b), "No overlap");
    }

    @Test
    public void testInside() {
        LongRange a = new LongRange(2000, 3000);
        LongRange b = new LongRange(1000, 4000);

        assertTrue(LongRange.isOverlapped(a, b), "Inside");
    }

    @Test
    public void testCover() {
        LongRange a = new LongRange(1000, 4000);
        LongRange b = new LongRange(2000, 3000);

        assertTrue(LongRange.isOverlapped(a, b), "Cover");
    }

    @Test
    public void testOverlapAtBegin() {
        LongRange a = new LongRange(1000, 3000);
        LongRange b = new LongRange(2000, 4000);

        assertTrue(LongRange.isOverlapped(a, b), "Overlap at begin");
    }

    @Test
    public void testOverlapAtEnd() {
        LongRange a = new LongRange(2000, 4000);
        LongRange b = new LongRange(1000, 3000);

        assertTrue(LongRange.isOverlapped(a, b), "Overlap at end");
    }

}
