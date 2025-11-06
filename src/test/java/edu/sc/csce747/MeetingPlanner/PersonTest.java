package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;

public class PersonTest {

	@Test
	public void testConstructorAndName() {
		Person p = new Person();
		assertNotNull(p);
		Person q = new Person("Drew");
		assertEquals("Drew", q.getName());
	}

	@Test
	public void testAddMeetingAndBusy() {
		Person p = new Person("Eve");
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(p);
		Meeting m = new Meeting(3, 2, 10, 11, attendees, new Room("R"), "Meet");
		try {
			p.addMeeting(m);
			assertTrue(p.isBusy(3, 2, 10, 11));

			Meeting m2 = new Meeting(3, 2, 10, 12, attendees, new Room("R2"), "Meet2");
			try {
				p.addMeeting(m2);
				fail("Expected TimeConflictException for attendee");
			} catch (TimeConflictException e) {
				assertTrue(e.getMessage().contains("Conflict for attendee Eve"));
			}
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

	@Test
	public void testPrintAgendaAndGetRemove() {
		Person p = new Person("Frank");
		try {
			Meeting m = new Meeting(4, 4, 9, 10, new ArrayList<Person>(), new Room(), "X");
			p.addMeeting(m);
			assertTrue(p.printAgenda(4).contains("X"));
			assertTrue(p.printAgenda(4, 4).contains("X"));
			assertNotNull(p.getMeeting(4, 4, 0));
			p.removeMeeting(4, 4, 0);
		} catch (TimeConflictException e) {
			fail("Unexpected: " + e.getMessage());
		}
	}

}
