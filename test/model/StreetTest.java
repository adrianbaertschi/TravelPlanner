package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StreetTest {
	
	@Test
	public void testGetLength() {
		Node k1 = new Node(0, 0);
		Node k2 = new Node(4, 3);
		
		Street s = new Street(k1, k2);
		assertEquals(5, s.getLenth());
		
		s = new Street(k2, k1);
		assertEquals(5, s.getLenth());
	}
	
	@Test
	public void testIsPointOnStreet() {
		Node k1 = new Node(0, 0);
		Node k2 = new Node(4, 4);
		
		Street s = new Street(k1, k2);
		
		assertTrue(s.isPointOnStreet(2, 2));
		assertTrue(s.isPointOnStreet(2, 1));
		
		assertFalse(s.isPointOnStreet(10, 4));
		assertFalse(s.isPointOnStreet(10, 10));
	}
}
