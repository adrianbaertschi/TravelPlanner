package dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.MapEditorModel;

public class MapEditorDao extends BaseDao {

	private static MapEditorDao instance;

	private EntityManager em;

	private MapEditorDao() { }

	public static MapEditorDao getInstance() {
		if (instance == null) {
			instance = new MapEditorDao();
		}
		return instance;
	}

	public MapEditorModel saveMap(MapEditorModel mapModel) {

		mapModel.setSaveDate(Calendar.getInstance());
		em = factory.createEntityManager();

		em.getTransaction().begin();
		em.persist(mapModel);
		em.getTransaction().commit();

		em.close();
		return mapModel;
	}
	/**
	 * Find all Maps
	 * @return
	 */
	public List<MapEditorModel> getMaps() {

		em = factory.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<MapEditorModel> query = em.createQuery("select m from MapEditorModel m", MapEditorModel.class);
		List<MapEditorModel> resultList = query.getResultList();

		em.close();
		return resultList;
	}

	public MapEditorModel getModelById(long id) {
		em = factory.createEntityManager();

		em.getTransaction().begin();
		MapEditorModel map = em.find(MapEditorModel.class, id);

		em.close();
		return map;
	}

}
