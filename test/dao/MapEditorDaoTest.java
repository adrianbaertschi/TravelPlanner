package dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import model.Knot;
import model.MapEditorModel;
import model.Street;

import org.junit.Test;

public class MapEditorDaoTest {
	
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
		
		map.setName("My Map" + System.currentTimeMillis());
		assertNull(map.getSaveDate());
		MapEditorDao.getInstance().saveMap(map);
		assertNotNull(map.getSaveDate());

	}
	
	@Test
	public void testGetMap() {
		List<MapEditorModel> maps = MapEditorDao.getInstance().getMaps();
		assertFalse(maps.isEmpty());
	}
	
	@Test
	public void testGetModelById() {
		MapEditorModel modelById = MapEditorDao.getInstance().getModelById(1);
		assertNotNull(modelById);
	}

}	