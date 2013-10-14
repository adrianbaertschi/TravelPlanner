package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Knot;
import model.MapEditorModel;
import model.Street;

public class Database {
	private static String PERSISTENCE_UNIT = "travelDB";
	
	private static Database instance;
	
	private Database() { }
	
	public static Database getInstance() {
		if(instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	public void saveMap(MapEditorModel mapModel) {
		
		EntityManager em = getEntityManager();
		
	    em.getTransaction().begin();
	    em.persist(mapModel);
	    em.getTransaction().commit();	
	    
	    em.close();
	}
	
	public void saveKnot(Knot k) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		EntityManager em = factory.createEntityManager();
		
	    em.getTransaction().begin();
	    
	    em.persist(k);
	    em.getTransaction().commit();

	    
	    em.close();
	}
	
	public void saveStreet(Street street) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		EntityManager em = factory.createEntityManager();
		
	    em.getTransaction().begin();
	    
	    em.persist(street);
	    em.getTransaction().commit();

	    
	    em.close();
	}
	
	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
		return factory.createEntityManager();
	}

}
