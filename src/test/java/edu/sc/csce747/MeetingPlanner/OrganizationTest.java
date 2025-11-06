package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class OrganizationTest {

	@Test
	public void testDefaultsAndLookup() {
		Organization org = new Organization();
		assertNotNull(org.getEmployees());
		assertTrue(org.getEmployees().size() >= 1);
		assertNotNull(org.getRooms());
		assertTrue(org.getRooms().size() >= 1);

		try {
			Room r = org.getRoom("2A01");
			assertEquals("2A01", r.getID());
		} catch (Exception e) {
			fail("Should find 2A01: " + e.getMessage());
		}

		try {
			Person p = org.getEmployee("Greg Gay");
			assertEquals("Greg Gay", p.getName());
		} catch (Exception e) {
			fail("Should find Greg Gay: " + e.getMessage());
		}

		try {
			org.getRoom("NOPE");
			fail("Expected exception for missing room");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Requested room does not exist"));
		}

		try {
			org.getEmployee("Nobody");
			fail("Expected exception for missing employee");
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("Requested employee does not exist"));
		}
	}

}
