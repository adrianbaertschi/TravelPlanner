package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StreetTest {
	
	@Test
	public void testGetLength() {
		Knot k1 = new Knot(0, 0);
		Knot k2 = new Knot(4, 3);
		
		Street s = new Street(k1, k2);
		assertEquals(5, s.getLenth());
		
		s = new Street(k2, k1);
		assertEquals(5, s.getLenth());
	}
}
