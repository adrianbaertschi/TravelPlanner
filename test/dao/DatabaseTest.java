package dao;

import model.Knot;
import model.MapEditorModel;
import model.Street;

import org.junit.Test;

public class DatabaseTest {
	
	@Test
	public void testSaveKnot() {
		Database.getInstance().saveKnot(new Knot(1, 1));
		Database.getInstance().saveKnot(new Knot(1, 1));
	}
	
	@Test
	public void testSaveStreet() {
		
		// Dreieck
		Knot k1 = new Knot(0, 0);
		Knot k2 = new Knot(0, 5);
		Knot k3 = new Knot(5, 0);
		
		Street s1 = new Street(k1, k2);
		Street s2 = new Street(k2, k3);
		Street s3 = new Street(k3, k1);
		
		Database.getInstance().saveStreet(s1);
		Database.getInstance().saveStreet(s2);
		Database.getInstance().saveStreet(s3);
		Database.getInstance().saveStreet(s3);
	}
	
	@Test
	public void testSaveMap() {
		MapEditorModel map = new MapEditorModel();
		
		// Dreieck
		Knot k1 = new Knot(0, 0);
		Knot k2 = new Knot(0, 5);
		Knot k3 = new Knot(5, 0);

		Street s1 = new Street(k1, k2);
		Street s2 = new Street(k2, k3);
		Street s3 = new Street(k3, k1);
		
		map.addStreet(s1);
		map.addStreet(s2);
		map.addStreet(s3);
		
		map.setName("My Map");
		
		Database.getInstance().saveMap(map);

	}
	

}
