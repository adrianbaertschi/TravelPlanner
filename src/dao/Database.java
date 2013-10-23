package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Knot;
import model.MapEditorModel;
import model.Street;

public class Database {
	private static String PERSISTENCE_UNIT = "travelDB";

	private static Database instance;

	private final EntityManagerFactory factory;
	private EntityManager em;

	private Database() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	}

	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public void saveMap(MapEditorModel mapModel) {

		em = factory.createEntityManager();

		em.getTransaction().begin();
		em.persist(mapModel);
		em.getTransaction().commit();

		em.close();
	}

	public void saveKnot(Knot k) {
		em = factory.createEntityManager();

		em.getTransaction().begin();

		em.persist(k);
		em.getTransaction().commit();

		em.close();
	}

	public void saveStreet(Street street) {
		em = factory.createEntityManager();
		em.getTransaction().begin();

		em.persist(street);
		em.getTransaction().commit();

		em.close();
	}

	public List<MapEditorModel> getMaps() {

		em = factory.createEntityManager();
		em.getTransaction().begin();

		Query q = em.createQuery("select m from MapEditorModel m");
		List<MapEditorModel> resultList = q.getResultList();

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
