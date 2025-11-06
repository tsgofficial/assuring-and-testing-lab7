package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;

import org.junit.Test;

public class PlannerInterfaceTest {

    @Test
    public void testInputOutputPrivateMethod() throws Exception {
        PlannerInterface pi = new PlannerInterface();

        java.io.InputStream oldIn = System.in;
        try {
            String input = "hello world\n";
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            Method m = PlannerInterface.class.getDeclaredMethod("inputOutput", String.class);
            m.setAccessible(true);
            String ret = (String) m.invoke(pi, "prompt");
            assertEquals("hello world", ret);
        } finally {
            System.setIn(oldIn);
        }
    }

}
