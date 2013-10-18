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
	
	private Database() { }
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	private EntityManager em = factory.createEntityManager();
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public void saveMap(MapEditorModel mapModel) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		em = factory.createEntityManager();
		
		
	    em.getTransaction().begin();
	    em.persist(mapModel);
	    em.getTransaction().commit();	
	    
	    em.close();
	}
	
	public void saveKnot(Knot k) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		em = factory.createEntityManager();
		
	    em.getTransaction().begin();
	    
	    em.persist(k);
	    em.getTransaction().commit();

	    
	    em.close();
	}
	
	public void saveStreet(Street street) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		em = factory.createEntityManager();
		
	    em.getTransaction().begin();
	    
	    em.persist(street);
	    em.getTransaction().commit();

	    
	    em.close();
	}
	

	public List<MapEditorModel> getMaps() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		em = factory.createEntityManager();
		
	    em.getTransaction().begin();
		
//		em.getTransaction().begin();
		Query q = em.createQuery("select m from MapEditorModel m");
		List resultList = q.getResultList();
		
		em.close();
		return resultList;
		
	}

}
