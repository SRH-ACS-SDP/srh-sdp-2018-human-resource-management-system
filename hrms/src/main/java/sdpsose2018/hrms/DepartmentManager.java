package sdpsose2018.hrms;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;



public class DepartmentManager {

	List<Country> countries;
	List<Location> location;
	List<Department> departments;
	
	EntityManager em;
	static Scanner sc;
	
	public DepartmentManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
		countries = em.createQuery("from Country", Country.class).getResultList();
	}
	
	public void addCountry() {
		Country co = new Country();
		
		boolean invalidInput = true;
		String name;
		
		do {
			System.out.println("Add Country Details");
			System.out.print("Country name: ");
			name = sc.next();
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
		String language = sc.next();
		co.setLanguage(language);
	
		System.out.print("Country currency: ");
		String currency = sc.next();
		co.setCurrency(currency);
		
		
		em.getTransaction().begin();
		em.persist(co);
		em.getTransaction().commit();
	
		Country dbObject = em.createQuery("from Country c where c.name='" + name + "'",Country.class).getSingleResult();
		
		System.out.println(dbObject.toString());
	}
	public  void viewCountry() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("_____View all available Countries_____");
		stringBuilder.append(String.format("%75s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-10s|\n", "COUNTRY ID","NAME", "LANGUAGE", "CURRENCY").replace(' ', '_'));
		
		for (Country c : countries) {
			
			stringBuilder.append(String.format("|%10d|", c.getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getLanguage()).replace(' ', '_'));
			stringBuilder.append(String.format("%-10s|", c.getCurrency()).replace(' ', '_'));
			

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}
	public void updateCountry() {
		try {
		System.out.println("Enter country name ");
		String name  = sc.next();
		Query query =em.createQuery("Select id from Country c where c.name LIKE :name");
		query.setParameter("name", name);
		
		List id  = query.getResultList();
		em.getTransaction().begin();
		Country c  = em.find(Country.class,id.get(0));
		
		/* Now we need to update the code*/
		
		String name_old  = c.name;
		String language_old  = c.language;
		String currency_old   =  c.currency;
		
		updatable(name_old,language_old,currency_old,c);
		System.out.println("Successfully Updated");
		em.getTransaction().commit();
		
		}catch(Exception e) {
			System.out.println("Errro");
		}
		
		
		
		
	}
	
	private static  void updatable(String name,String language,String currency,Country co)
	{
		
		int  count  =0;
		System.out.println("Please Enter the new name if you do not want to change the name either leave it blank or type no ");
		String new_name =  sc.next();
		if (new_name.equals(" ")|| new_name.toLowerCase().equals("no")) {
			
			co.setName(name);
			count++;
		}else {
			co.setName(new_name);
			count++;
		}
		
		System.out.println("Please Enter the new language if you do not want to change the language either leave it blank or type no ");
		String new_language=  sc.next();
		if (new_language.equals(" ")|| new_language.toLowerCase().equals("no")) {
			
			co.setLanguage(language);
		}else {
			co.setLanguage(new_language);
		}
		
		
		System.out.println("Please Enter the new Currency if you do not want to change the currency either leave it blank or type no ");
		String new_curreny =  sc.next();
		if (new_curreny.equals(" ") || new_curreny.toLowerCase().equals("no")) {
			
			co.setCurrency(currency);
		}else {
			co.setCurrency(new_curreny);
		}
		
		
	}
	
	public void deleteCountry() {
		
	}
}
