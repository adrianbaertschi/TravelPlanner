package model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MapEditorModelTest {
	
	@Test
	public void testAddStreet() {
		MapEditorModel model = new MapEditorModel();
		Street s1 = new Street(new Node(10, 20), new Node(30, 40));
		model.addStreet(s1);
		
		model.addStreet(new Street(new Node(10, 20), new Node(40, 10)));
		
		boolean exception = false;
		try {
			model.addStreet(new Street(new Node(30, 40), new Node(10, 20)));
		} catch (MapEditorModelException e) {
			exception = true;
		}
		
		assertTrue(exception);
		
	}

}
