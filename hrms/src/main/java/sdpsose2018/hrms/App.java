package sdpsose2018.hrms;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager em = emf.createEntityManager();
    	
    	
    	boolean dbHasCountry = false;
    	try {
    		em.createQuery("from Country where name = 'Germany'").getSingleResult();    		
    	} catch (NoResultException nre) {
    		dbHasCountry = true;
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	if (dbHasCountry) {
    	Country country = new Country();
    	country.setName("Germany");
    	country.setLanguage("german");
    	country.setCurrency("EUR");
    	
    	em.getTransaction().begin();
    	em.persist(country);
    	em.getTransaction().commit();
    	}
    	
    	Country c = (Country) em.createQuery("select c from Country c where c.name = 'Germany'").getSingleResult();  	
    	
    	boolean dbHasLocation = false;
    	try {
    		em.createQuery("from Location where name = 'Sebis'' home'").getSingleResult();    		
    	} catch (NoResultException nre) {
    		dbHasLocation = true;
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    	}

    	if(dbHasLocation) {
    	Location location = new Location();
    	location.setName("Sebis' home");
    	location.setAddress("76661, Philippsburg-Rheinsheim, Oskar-Frey Str. 16");
    	location.setDetails("Home sweet home");
    	location.setCountryId(c.getId());
    	
    	em.getTransaction().begin();
    	em.persist(location);
    	em.getTransaction().commit();
    	}
    	
    	Location l = em.find(Location.class, em.createQuery("select l from Location l where l.address = '76661, Philippsburg-Rheinsheim, Oskar-Frey Str. 16'").setFirstResult(1).getFirstResult());
    	
    	boolean dbHasDepartment = false;
    	try {
    		em.createQuery("from Department where name = 'Kitchen'").getSingleResult();    		
    	} catch (NoResultException nre) {
    		dbHasDepartment = true;
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	if(dbHasDepartment) {
    	Department department = new Department();
    	department.setName("Kitchen");
    	department.setDescription("Sebis' kitchen");
    	department.setAddress("76661, Philippsburg-Rheinsheim, Oskar-Frey Str. 16");
    	department.setDescription("The best place to find food.");
    	department.setLocationId(l.getId());
    	
    	em.getTransaction().begin();
    	em.persist(department);
    	em.getTransaction().commit();
    	}
        
    	Department d = em.find(Department.class, em.createQuery("select d from Department d where d.name = 'kitchen'").setFirstResult(1).getFirstResult());
    	
    	em.close();
    	
        System.out.println(c.toString());
        System.out.println(l.toString());
        System.out.println(d.toString());
    }
}
