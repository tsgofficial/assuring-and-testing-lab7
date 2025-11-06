package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class CalendarTest {

	@Test
	public void testAddMeeting_holiday() {

		Calendar calendar = new Calendar();

		try {
			Meeting midsommar = new Meeting(6, 26, "Midsommar");
			calendar.addMeeting(midsommar);

			Boolean added = calendar.isBusy(6, 26, 0, 23);
			assertTrue("Midsommar should be marked as busy on the calendar", added);
		} catch (TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}

	@Test
	public void testAddAndConflict() {
		Calendar calendar = new Calendar();
		try {
			Meeting m1 = new Meeting(6, 10, 9, 11, new java.util.ArrayList<Person>(), new Room(), "A");
			calendar.addMeeting(m1);

			Meeting m2 = new Meeting(6, 10, 10, 12, new java.util.ArrayList<Person>(), new Room(), "B");
			try {
				calendar.addMeeting(m2);
				fail("Expected TimeConflictException due to overlap");
			} catch (TimeConflictException e) {
				assertTrue(e.getMessage().contains("Overlap"));
			}
		} catch (TimeConflictException e) {
			fail("Should not have thrown for initial adds: " + e.getMessage());
		}
	}

	@Test
	public void testCheckTimesValidation() {

		try {
			Calendar.checkTimes(1, 0, 0, 1);
			fail("Expected TimeConflictException for invalid day");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(12, 1, 0, 1);
			fail("Expected TimeConflictException for invalid month");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(1, 1, -1, 1);
			fail("Expected TimeConflictException for invalid start");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(1, 1, 0, 24);
			fail("Expected TimeConflictException for invalid end");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(1, 1, 5, 5);
			fail("Expected TimeConflictException for start >= end");
		} catch (TimeConflictException e) {
		}
	}

	@Test
	public void testClearPrintGetRemove() {
		Calendar cal = new Calendar();
		try {
			Meeting m = new Meeting(5, 3, 8, 9, new java.util.ArrayList<Person>(), new Room("RM"), "Test");
			cal.addMeeting(m);
			assertTrue(cal.isBusy(5, 3, 8, 9));
			String monthAgenda = cal.printAgenda(5);
			assertTrue(monthAgenda.contains("Test"));
			String dayAgenda = cal.printAgenda(5, 3);
			assertTrue(dayAgenda.contains("Test"));
			Meeting got = cal.getMeeting(5, 3, 0);
			assertTrue(got.getDescription().equals("Test"));
			cal.removeMeeting(5, 3, 0);
			cal.clearSchedule(5, 3);
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

	@Test
	public void testCheckTimesAdditionalInvalids() {

		try {
			Calendar.checkTimes(1, 32, 0, 1);
			fail("Expected TimeConflictException for day > 31");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(1, 1, 24, 25);
			fail("Expected TimeConflictException for start > 23");
		} catch (TimeConflictException e) {
		}

		try {
			Calendar.checkTimes(1, 1, 0, -1);
			fail("Expected TimeConflictException for end < 0");
		} catch (TimeConflictException e) {
		}
	}

	@Test
	public void testIsBusyStartAndEndInsideExisting() {
		Calendar calendar = new Calendar();
		try {

			Meeting m = new Meeting(5, 5, 9, 12, new java.util.ArrayList<Person>(), new Room(), "M");
			calendar.addMeeting(m);

			assertTrue(calendar.isBusy(5, 5, 10, 11));

			assertTrue(calendar.isBusy(5, 5, 8, 11));
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

	@Test
	public void testAddMeetingSkipsDayDoesNotExistEntry() {
		Calendar calendar = new Calendar();
		try {

			Meeting m = new Meeting(2, 29, 10, 11, new java.util.ArrayList<Person>(), new Room(), "Real");
			calendar.addMeeting(m);
			assertTrue(calendar.isBusy(2, 29, 10, 11));
		} catch (TimeConflictException e) {
			fail("Should not throw when adding to a pre-populated 'Day does not exist' slot: " + e.getMessage());
		}
	}

	@Test
	public void testCheckTimesMonthLowerBound() {
		try {
			Calendar.checkTimes(0, 1, 0, 1);
			fail("Expected TimeConflictException for month < 1");
		} catch (TimeConflictException e) {
		}
	}

	@Test
	public void testAddAndConflict_EndInsideExisting() {
		Calendar calendar = new Calendar();
		try {
			Meeting m1 = new Meeting(6, 12, 9, 12, new java.util.ArrayList<Person>(), new Room(), "Existing");
			calendar.addMeeting(m1);

			Meeting m2 = new Meeting(6, 12, 8, 10, new java.util.ArrayList<Person>(), new Room(), "OverlapEnd");
			try {
				calendar.addMeeting(m2);
				fail("Expected TimeConflictException due to overlap (end inside existing)");
			} catch (TimeConflictException e) {
				assertTrue(e.getMessage().contains("Overlap"));
			}
		} catch (TimeConflictException e) {
			fail("Should not have thrown for initial adds: " + e.getMessage());
		}
	}
}
