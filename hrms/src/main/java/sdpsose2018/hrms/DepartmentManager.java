package sdpsose2018.hrms;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class DepartmentManager {

	List<Country> countries;
	List<Location> location;
	List<Department> departments;
	
	EntityManager em;
	Scanner sc;
	
	public DepartmentManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
		
	}
	
	public void addCountry() {
		Country co = new Country();
		
		boolean invalidInput = true;
		String name;
		
		do {
			System.out.print("Country name: ");
			name = sc.nextLine();
			co.setName(name);
	    	try {
	    		em.createQuery("from Country where name = '" + name + "'").getSingleResult();    		
	    		System.out.println("Country already exists in Database.");
	    		boolean isRetry = true;
	    		do {
		    		System.out.print("Try again? (y/n) ");
		    		switch(sc.nextLine().charAt(0)) {
		    		case 'n':
		    			return;
		    		case 'y':
		    			isRetry = true;
		    			break;
		    		default:
		    			isRetry = false;
		    			break;
		    			}
	    		} while(!isRetry);
	    		
	    	} catch (NoResultException nre) {
	    		invalidInput = false;
	    	}
	    	catch (Exception e){
	    		System.out.println(e.getMessage());
	    	}
		} while (invalidInput);
			
		System.out.print("Country language: ");
		String language = sc.nextLine();
		co.setLanguage(language);
	
		System.out.print("Country currency: ");
		String currency = sc.nextLine();
		co.setCurrency(currency);
		
		
		em.getTransaction().begin();
		em.persist(co);
		em.getTransaction().commit();
	
		Country dbObject = em.createQuery("from Country c where c.name='" + name + "'",Country.class).getSingleResult();
		
		System.out.println(dbObject.toString());
	}
	
}
