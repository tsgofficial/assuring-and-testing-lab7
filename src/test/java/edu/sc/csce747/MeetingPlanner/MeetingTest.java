package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class MeetingTest {

	@Test
	public void testFullConstructorAndToString() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		Person p = new Person("Alice");
		attendees.add(p);
		Room r = new Room("R1");
		Meeting m = new Meeting(6, 15, 9, 10, attendees, r, "Sprint planning");

		assertEquals(6, m.getMonth());
		assertEquals(15, m.getDay());
		assertEquals(9, m.getStartTime());
		assertEquals(10, m.getEndTime());
		assertEquals("Sprint planning", m.getDescription());
		assertEquals(r, m.getRoom());
		assertNotNull(m.getAttendees());

		String s = m.toString();
		assertTrue(s.contains("6/15"));
		assertTrue(s.contains("R1"));
		assertTrue(s.contains("Alice"));
	}

	@Test
	public void testDayBlockConstructor() {
		Meeting m = new Meeting(7, 4, "Independence");
		assertEquals(0, m.getStartTime());
		assertEquals(23, m.getEndTime());
		assertEquals("Independence", m.getDescription());
	}

	@Test
	public void testAddRemoveAttendeeAndSetters() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(new Person("Bob"));
		Room r = new Room("RoomX");
		Meeting m = new Meeting(8, 1, 14, 15, attendees, r, "One-on-one");

		Person c = new Person("Carol");
		m.addAttendee(c);
		assertTrue(m.getAttendees().contains(c));

		m.removeAttendee(c);
		assertFalse(m.getAttendees().contains(c));

		m.setDescription("Updated");
		assertEquals("Updated", m.getDescription());
	}

	@Test
	public void testSetMethods() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		Room r = new Room("Initial");
		Meeting m = new Meeting(1, 1, 9, 10, attendees, r, "Initial");

		m.setMonth(12);
		assertEquals(12, m.getMonth());

		m.setDay(31);
		assertEquals(31, m.getDay());

		m.setStartTime(8);
		assertEquals(8, m.getStartTime());

		m.setEndTime(17);
		assertEquals(17, m.getEndTime());

		Room newRoom = new Room("UpdatedRoom");
		m.setRoom(newRoom);
		assertEquals(newRoom, m.getRoom());

		m.setDescription("Updated Desc");
		assertEquals("Updated Desc", m.getDescription());
	}

	@Test
	public void testToStringNoAttendeesShowsNone() {

		Meeting m = new Meeting(3, 10, 11, 12);
		m.setRoom(null);
		m.setDescription(null);

		String s = m.toString();
		assertTrue(s.contains("3/10"));
		assertTrue(s.contains("11 - 12"));

		assertTrue(s.contains("Attending: None"));
	}

	@Test
	public void testToStringMultipleAttendeesFormatting() {
		ArrayList<Person> attendees = new ArrayList<Person>();
		attendees.add(new Person("Alice"));
		attendees.add(new Person("Bob"));
		Room r = new Room("R2");
		Meeting m = new Meeting(4, 20, 9, 10, attendees, r, "Planning");

		String s = m.toString();

		assertTrue(s.contains("4/20"));
		assertTrue(s.contains("R2"));
		assertTrue(s.contains("Planning"));

		assertTrue(s.contains("Alice,Bob"));
		assertFalse(s.trim().endsWith(","));
	}

	@Test
	public void testToStringWholeDayBlockFormat() {
		Meeting m = new Meeting(7, 4, "Independence Day");
		String s = m.toString();
		assertTrue(s.contains("7/4"));

		assertTrue(s.contains("0 - 23"));
		assertTrue(s.contains("Independence Day"));
	}

	@Test
	public void testToStringRoomWithNullID() {

		ArrayList<Person> attendees = new ArrayList<Person>();
		Room r = new Room(null);
		Meeting m = new Meeting(5, 2, 10, 11, attendees, r, "Desc");

		String s = m.toString();
		assertTrue(s.contains("5/2"));
		assertTrue(s.contains("10 - 11"));

		assertTrue(s.contains(": Desc") && (s.contains(",: Desc") || s.contains(", : Desc")));

		assertTrue(s.contains("Attending: None"));
	}

	@Test
	public void testDayBlockConstructorNoDescription() {

		Meeting m = new Meeting(2, 14);

		assertEquals(0, m.getStartTime());
		assertEquals(23, m.getEndTime());

		assertEquals(2, m.getMonth());
		assertEquals(14, m.getDay());

		assertNull(m.getDescription());
		assertNull(m.getRoom());
		assertNull(m.getAttendees());

		String s = m.toString();
		assertTrue(s.contains("2/14"));
		assertTrue(s.contains("0 - 23"));
		assertTrue(s.contains("Attending: None"));
	}
}
