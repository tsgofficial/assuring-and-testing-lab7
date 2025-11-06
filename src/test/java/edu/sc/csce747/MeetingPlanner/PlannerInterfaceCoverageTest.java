package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.Test;

public class PlannerInterfaceCoverageTest {

    static class TestPlannerInterface extends PlannerInterface {
        private String[] responses;
        private int idx = 0;
        public boolean scheduleCalled = false;

        public TestPlannerInterface(String... responses) {
            super();
            this.responses = responses == null ? new String[0] : responses;
        }

        @Override
        protected String inputOutput(String message) {
            if (idx < responses.length)
                return responses[idx++];
            return "";
        }

        @Override
        public void mainMenu() {
        }

        @Override
        public void scheduleMeeting() {
            scheduleCalled = true;
            super.scheduleMeeting();
        }
    }

    @Test
    public void testScheduleMeeting_createsMeeting() throws Exception {

        TestPlannerInterface pi = new TestPlannerInterface("5", "10", "9", "10", "2A01", "Greg Gay", "done",
                "TeamMeeting");

        pi.scheduleMeeting();

        Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(pi);
        Room r = org.getRoom("2A01");
        Meeting m = r.getMeeting(5, 10, 0);
        assertNotNull(m);
        assertEquals("TeamMeeting", m.getDescription());
    }

    @Test
    public void testCheckRoomAvailability_printsRooms() throws Exception {
        TestPlannerInterface pi = new TestPlannerInterface("5", "10", "9", "10");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            pi.checkRoomAvailability();
            String out = bout.toString();

            assertTrue(out.contains("2A01"));
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testCheckEmployeeAvailability_printsNames() throws Exception {
        TestPlannerInterface pi = new TestPlannerInterface("5", "10", "9", "10");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            pi.checkEmployeeAvailability();
            String out = bout.toString();

            assertTrue(out.contains("Greg Gay"));
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testCheckAgendaRoom_printsAgenda() throws Exception {
        TestPlannerInterface pi = new TestPlannerInterface("5", "all", "2A01");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            pi.checkAgendaRoom();
            String out = bout.toString();
            assertTrue(out.contains("Agenda for"));
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testCheckAgendaPerson_printsAgenda() throws Exception {
        TestPlannerInterface pi = new TestPlannerInterface("5", "all", "Greg Gay");
        PrintStream oldOut = System.out;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bout));
            pi.checkAgendaPerson();
            String out = bout.toString();
            assertTrue(out.contains("Agenda for"));
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testMainMenu_callsScheduleBranch() throws Exception {

        class MainMenuTester extends PlannerInterface {
            private String[] responses;
            private int idx = 0;
            public boolean scheduleCalled = false;

            public MainMenuTester(String... responses) {
                super();
                this.responses = responses;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void scheduleMeeting() {
                scheduleCalled = true;
            }
        }

        MainMenuTester tester = new MainMenuTester("1");
        tester.mainMenu();
        assertTrue(tester.scheduleCalled);
    }

    @Test
    public void testMainMenu_callsVacationBranch() throws Exception {
        class VTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public VTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void scheduleVacation() {
                called = true;
            }
        }
        VTester t = new VTester("2");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_callsCheckRoomBranch() throws Exception {
        class RTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public RTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void checkRoomAvailability() {
                called = true;
            }
        }
        RTester t = new RTester("3");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_callsCheckEmployeeBranch() throws Exception {
        class ETester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public ETester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void checkEmployeeAvailability() {
                called = true;
            }
        }
        ETester t = new ETester("4");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_callsCheckAgendaRoomBranch() throws Exception {
        class ARTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public ARTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void checkAgendaRoom() {
                called = true;
            }
        }
        ARTester t = new ARTester("5");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_callsCheckAgendaPersonBranch() throws Exception {
        class APTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public APTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void checkAgendaPerson() {
                called = true;
            }
        }
        APTester t = new APTester("6");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_handlesNonNumberThenSchedule() throws Exception {
        class NNTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public NNTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void scheduleMeeting() {
                called = true;
            }
        }
        NNTester t = new NNTester("notanumber", "1");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test
    public void testMainMenu_handlesInvalidNumberThenSchedule() throws Exception {
        class INTester extends PlannerInterface {
            private String[] responses;
            int idx = 0;
            public boolean called = false;

            public INTester(String... r) {
                super();
                responses = r;
            }

            @Override
            protected String inputOutput(String message) {
                if (idx < responses.length)
                    return responses[idx++];
                return "";
            }

            @Override
            public void scheduleMeeting() {
                called = true;
            }
        }
        INTester t = new INTester("99", "1");
        t.mainMenu();
        assertTrue(t.called);
    }

    @Test(expected = SecurityException.class)
    public void testMain_static_callsExit() throws Exception {
        final SecurityManager orig = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
            }

            @Override
            public void checkExit(int status) {
                throw new SecurityException("exit:" + status);
            }
        });
        try {
            java.io.InputStream oldIn = System.in;
            try {
                System.setIn(new ByteArrayInputStream("0\n".getBytes()));
                PlannerInterface.main(new String[] {});
            } finally {
                System.setIn(oldIn);
            }
        } finally {
            System.setSecurityManager(orig);
        }
    }

    @Test(expected = SecurityException.class)
    public void testMain_triggersExit() throws Exception {

        final SecurityManager orig = System.getSecurityManager();
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPermission(java.security.Permission perm) {
            }

            @Override
            public void checkExit(int status) {
                throw new SecurityException("exit:" + status);
            }
        });
        try {

            PlannerInterface exitTester = new PlannerInterface() {
                private int idx = 0;

                @Override
                protected String inputOutput(String message) {

                    if (idx == 0) {
                        idx++;
                        return "0";
                    }
                    return "";
                }
            };

            exitTester.mainMenu();
        } finally {
            System.setSecurityManager(orig);
        }
    }

    @Test
    public void testInputOutput_handlesIOExceptionAndCallsMainMenu() throws Exception {

        class IOTester extends PlannerInterface {
            public boolean mainCalled = false;

            @Override
            protected String inputOutput(String message) {
                return super.inputOutput(message);
            }

            @Override
            public void mainMenu() {
                mainCalled = true;
            }
        }

        IOTester pi = new IOTester();

        java.io.InputStream oldIn = System.in;
        try {
            System.setIn(new java.io.InputStream() {
                @Override
                public int read() throws java.io.IOException {
                    throw new java.io.IOException("boom");
                }
            });

            String ret = pi.inputOutput("prompt");

            assertEquals("", ret);
            assertTrue(pi.mainCalled);
        } finally {
            System.setIn(oldIn);
        }
    }

    @Test
    public void testCheckRoomAvailability_handlesTimeConflictException() throws Exception {
        final boolean[] mainCalled = { false };

        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "24", "0") {
            @Override
            public void mainMenu() {
                mainCalled[0] = true;
            }
        };
        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.checkRoomAvailability();

            assertTrue(mainCalled[0]);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testCheckEmployeeAvailability_handlesTimeConflictException() throws Exception {
        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "24", "0") {
            @Override
            public void mainMenu() {
            }
        };
        final boolean[] mainCalled = { false };
        spy = new TestPlannerInterface("5", "10", "24", "0") {
            @Override
            public void mainMenu() {
                mainCalled[0] = true;
            }
        };
        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.checkEmployeeAvailability();
            assertTrue(mainCalled[0]);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleMeeting_invalidRoomThenRoomAddConflict() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("6", "10", "9", "10", "NOPE", "2A01", "Greg Gay", "done",
                "Conf") {
            @Override
            public void mainMenu() {
            }
        };

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Room r = org.getRoom("2A01");
        Meeting existing = new Meeting(6, 10, 9, 10, new java.util.ArrayList<Person>(), new Room(), "Existing");
        r.addMeeting(existing);

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.scheduleMeeting();
            String out = bout.toString();

            assertTrue(out.length() > 0);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleVacation_cancelAndConflictDetected() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "5", "12", "cancel") {
            @Override
            public void mainMenu() {
            }
        };
        spy.scheduleVacation();

        final boolean[] conflictMainCalled = { false };
        TestPlannerInterface spy2 = new TestPlannerInterface("5", "10", "5", "12", "Greg Gay") {
            @Override
            public void mainMenu() {
                conflictMainCalled[0] = true;
            }
        };

        java.lang.reflect.Field orgField2 = PlannerInterface.class.getDeclaredField("org");
        orgField2.setAccessible(true);
        Organization org2 = (Organization) orgField2.get(spy2);
        Person greg = org2.getEmployee("Greg Gay");
        greg.addMeeting(new Meeting(5, 11, 0, 23, new java.util.ArrayList<Person>(), new Room(), "Busy"));

        spy2.scheduleVacation();

        assertTrue(conflictMainCalled[0]);
    }

    @Test
    public void testCheckAgendaRoomAndPerson_invalidNamesPrintError() throws Exception {
        TestPlannerInterface piRoom = new TestPlannerInterface("5", "all", "NOPE");
        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            piRoom.checkAgendaRoom();
            String out = bout.toString();
            assertTrue(out.length() > 0);
        } finally {
            System.setOut(oldOut);
        }

        TestPlannerInterface piPerson = new TestPlannerInterface("5", "all", "NoSuchPerson");
        oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            piPerson.checkAgendaPerson();
            String out = bout.toString();
            assertTrue(out.length() > 0);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleVacation_conflictsInInterveningAndEndMonths() throws Exception {

        final boolean[] mainCalled = { false };
        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "7", "2", "Greg Gay") {
            @Override
            public void mainMenu() {
                mainCalled[0] = true;
            }
        };

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");

        greg.addMeeting(new Meeting(6, 3, 0, 23, new java.util.ArrayList<Person>(), new Room(), "BusyIntervening"));
        greg.addMeeting(new Meeting(7, 2, 0, 23, new java.util.ArrayList<Person>(), new Room(), "BusyEnd"));

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.scheduleVacation();
            String out = bout.toString();

            assertTrue(out.contains("There is a conflict for date 6/3") || out.contains("BusyIntervening"));
            assertTrue(out.contains("There is a conflict for date 7/2") || out.contains("BusyEnd"));
            assertTrue(mainCalled[0]);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleVacation_bookingSuccessSameMonth() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "5", "12", "Greg Gay");

        spy.scheduleVacation();

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");

        assertEquals("vacation", greg.getMeeting(5, 10, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(5, 11, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(5, 12, 0).getDescription());
    }

    @Test
    public void testScheduleVacation_bookingAddMeetingThrowsTriggersMainMenu() throws Exception {

        final boolean[] mainCalled = { false };
        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "5", "12", "Greg Gay") {
            @Override
            public void mainMenu() {
                mainCalled[0] = true;
            }
        };

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");
        greg.addMeeting(new Meeting(5, 11, 9, 10, new java.util.ArrayList<Person>(), new Room(), "Busy"));

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.scheduleVacation();
            String out = bout.toString();

            assertTrue(out.contains("Conflict for attendee") || out.length() > 0);
            assertTrue(mainCalled[0]);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleVacation_bookingAcrossMonthsSuccess() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("5", "30", "6", "2", "Greg Gay");

        spy.scheduleVacation();

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");

        assertEquals("vacation", greg.getMeeting(5, 30, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(5, 31, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(6, 1, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(6, 2, 0).getDescription());
    }

    @Test
    public void testScheduleVacation_conflictInStartMonth() throws Exception {

        final boolean[] mainCalled = { false };
        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "7", "2", "Greg Gay") {
            @Override
            public void mainMenu() {
                mainCalled[0] = true;
            }
        };

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");
        greg.addMeeting(new Meeting(5, 10, 0, 23, new java.util.ArrayList<Person>(), new Room(), "BusyStart"));

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            spy.scheduleVacation();
            String out = bout.toString();

            assertTrue(out.contains("There is a conflict for date 5/10") || out.contains("BusyStart"));
            assertTrue(mainCalled[0]);
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleVacation_addsMeetingsForInterveningMonths() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("3", "10", "7", "2", "Greg Gay");

        spy.scheduleVacation();

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");

        assertEquals("vacation", greg.getMeeting(3, 10, 0).getDescription());

        assertEquals("vacation", greg.getMeeting(4, 1, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(4, 30, 0).getDescription());

        assertEquals("vacation", greg.getMeeting(5, 1, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(5, 31, 0).getDescription());

        assertEquals("vacation", greg.getMeeting(6, 1, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(6, 30, 0).getDescription());

        assertEquals("vacation", greg.getMeeting(7, 1, 0).getDescription());
        assertEquals("vacation", greg.getMeeting(7, 2, 0).getDescription());
    }

    @Test
    public void testScheduleMeeting_isBusyThrows_callsMainMenu() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("5", "10", "24", "0") {
            @Override
            public void mainMenu() {

                throw new RuntimeException("STOP");
            }
        };

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            try {
                spy.scheduleMeeting();
                fail("expected RuntimeException from overridden mainMenu");
            } catch (RuntimeException e) {
                assertEquals("STOP", e.getMessage());
            }
        } finally {
            System.setOut(oldOut);
        }
    }

    @Test
    public void testScheduleMeeting_attendeeAddMeetingThrows_callsMainMenu() throws Exception {

        TestPlannerInterface spy = new TestPlannerInterface("6", "10", "9", "10", "2A01", "Greg Gay", "done",
                "Desc") {
            @Override
            public void mainMenu() {

                throw new RuntimeException("STOP");
            }
        };

        java.lang.reflect.Field orgField = PlannerInterface.class.getDeclaredField("org");
        orgField.setAccessible(true);
        Organization org = (Organization) orgField.get(spy);
        Person greg = org.getEmployee("Greg Gay");
        greg.addMeeting(new Meeting(6, 10, 9, 10, new java.util.ArrayList<Person>(), new Room(), "Busy"));

        java.io.PrintStream oldOut = System.out;
        try {
            java.io.ByteArrayOutputStream bout = new java.io.ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(bout));
            try {
                spy.scheduleMeeting();
                fail("expected RuntimeException from overridden mainMenu");
            } catch (RuntimeException e) {
                assertEquals("STOP", e.getMessage());
                String out = bout.toString();

                assertTrue(out.length() > 0);
            }
        } finally {
            System.setOut(oldOut);
        }
    }
}
