package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Base class for all DAO Objects.
 * 
 * @author adrian
 *
 */
public abstract class BaseDao<T> {
	private String PERSISTENCE_UNIT = "travelDB";
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager em = factory.createEntityManager();
	
	public abstract T getModelById(long id);
	
}