package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Base class for all DAO Implementations.
 * 
 * @param <T> Model class
 */
public abstract class BaseDao<T> {
	private String PERSISTENCE_UNIT = "travelDB";
	
	EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
	EntityManager em = factory.createEntityManager();
	
	/**
	 * Get model instance by primary key
	 * 
	 * @param id ID
	 * @return Model instance
	 */
	public abstract T getModelById(long id);
	
	/**
	 * Get model instance by primary key
	 * 
	 * @param id ID
	 */
	public abstract void deleteModelById(long id);

}