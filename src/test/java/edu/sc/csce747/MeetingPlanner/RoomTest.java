package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class RoomTest {

	@Test
	public void testConstructorAndID() {
		Room r = new Room();
		assertNotNull(r);
		Room s = new Room("RM1");
		assertEquals("RM1", s.getID());
	}

	@Test
	public void testAddMeetingAndBusy() {
		Room r = new Room("RoomA");
		ArrayList<Person> attendees = new ArrayList<Person>();
		Meeting m = new Meeting(2, 2, 8, 9, attendees, new Room(), "RMeet");
		try {
			r.addMeeting(m);
			assertTrue(r.isBusy(2, 2, 8, 9));

			Meeting m2 = new Meeting(2, 2, 8, 10, attendees, new Room(), "RMeet2");
			try {
				r.addMeeting(m2);
				fail("Expected TimeConflictException for room");
			} catch (TimeConflictException e) {
				assertTrue(e.getMessage().contains("Conflict for room RoomA"));
			}
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

	@Test
	public void testAgendaGetRemove() {
		Room r = new Room("RZ");
		try {
			Meeting m = new Meeting(1, 5, 7, 8, new ArrayList<Person>(), new Room(), "ABC");
			r.addMeeting(m);
			assertTrue(r.printAgenda(1).contains("ABC"));
			assertTrue(r.printAgenda(1, 5).contains("ABC"));
			assertNotNull(r.getMeeting(1, 5, 0));
			r.removeMeeting(1, 5, 0);
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

}
