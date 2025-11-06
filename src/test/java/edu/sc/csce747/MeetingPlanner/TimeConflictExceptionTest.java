package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeConflictExceptionTest {

    @Test
    public void testConstructors() {
        TimeConflictException e1 = new TimeConflictException();
        assertNotNull(e1);

        TimeConflictException e2 = new TimeConflictException("msg");
        assertEquals("msg", e2.getMessage());

        Throwable cause = new RuntimeException("cause");
        TimeConflictException e3 = new TimeConflictException(cause);
        assertEquals(cause, e3.getCause());

        TimeConflictException e4 = new TimeConflictException("m", cause);
        assertEquals("m", e4.getMessage());

        TimeConflictException e5 = new TimeConflictException("m2", cause, true, false);
        assertEquals("m2", e5.getMessage());
    }

}
