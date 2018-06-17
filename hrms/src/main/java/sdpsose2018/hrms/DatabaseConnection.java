package sdpsose2018.hrms;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class DatabaseConnection {

	private static DatabaseConnection instance;
	private EntityManager entityManager;
	
	private DatabaseConnection() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
		entityManager = emf.createEntityManager();
	}
	
    public <T> T find(Class<T> entityClass, Object primaryKey) {
		return this.entityManager.find(entityClass, primaryKey);
	}
    
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
    	return this.entityManager.createQuery(qlString, resultClass);
    }
    
    public Query createNativeQuery(String sqlString) {
    	return this.entityManager.createNativeQuery(sqlString);
    }
    
    public Query createQuery(String qlString) {
    	return this.entityManager.createQuery(qlString);
    }

    public <T> T merge(T entity) {
    	return this.entityManager.merge(entity);
    }

	public void persist(Object object) {
		this.entityManager.getTransaction().begin();
		this.entityManager.persist(object);
		this.entityManager.flush();
		this.entityManager.clear();
		this.entityManager.getTransaction().commit();
	}
	
	public void remove(Object object) {
		this.entityManager.getTransaction().begin();
		this.entityManager.remove(object);
		this.entityManager.flush();
		this.entityManager.clear();
		this.entityManager.getTransaction().commit();	}
	
	public static DatabaseConnection getInstance() {
		if(instance == null){
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	protected void finalize() {
		instance.entityManager.close();
		instance = null;
	}
	
}
