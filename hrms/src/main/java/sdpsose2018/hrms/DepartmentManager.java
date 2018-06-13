<<<<<<< HEAD
package sdpsose2018.hrms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import antlr.StringUtils;



public class DepartmentManager {

	List<Country> countries;
	List<Location> location;
	List<Department> departments;
	public static String d_name ="";
	static BufferedReader r ;
	
	EntityManager em;
	static Scanner sc;
	
	@SuppressWarnings("unchecked")
	
	public DepartmentManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
		countries = em.createQuery("from Country", Country.class).getResultList();
		location  =  em.createQuery("from Location",Location.class).getResultList();
		departments  = em.createQuery("from Department",Department.class).getResultList();
		
		r = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public void addCountry() throws IOException {
		Country co = new Country();
		
		boolean invalidInput = true;
		String name;
		
		do {
			System.out.println("Add Country Details");
			System.out.print("Country Name: ");
			name = sc.next();
			if(ischeckChar(name)==true) {
			co.setName(name);
	    	try {
	    		em.createQuery("from Country where name = '" + name + "'").getSingleResult();    		
	    		System.out.println("Country Already exists in Database.");
	    		boolean isRetry = true;
	    		do {
		    		System.out.print("Try Again.? (y/n) ");
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
			}else {
				System.out.println("Wrong Input!");
			}
		} while (invalidInput);
		
		boolean input_type_ = true;
		do {
		System.out.print("Country Language: ");
		String language = r.readLine();
		if(ischeckChar(language)==false) {
			System.out.println("Enter Only Strings");
		}else {
		co.setLanguage(language);
		input_type_ = false;
		}
		}while(input_type_);
		
		boolean input_type = true;
		do {
		
		System.out.print("Country Currency: like [USD,EUR,INR,RUS] etc ");
		String currency = r.readLine();	
		
		if(currency.length() >3 || ischeckChar(currency)==false) {
			System.out.println("Invalid input ");
		}else {
			co.setCurrency(currency);
			input_type=false;
		}
		
	
		}while(input_type);
		
		
		boolean input = true;
		do {
		try {
			
		
			
			
		System.out.println("Enter Tax Rate");	
		String  tax  =  r.readLine();
		co.setTaxRate(Double.parseDouble(tax));
		
		em.getTransaction().begin();
		em.flush();
		em.clear();
		em.persist(co);
		em.getTransaction().commit();
	
		Country dbObject = em.createQuery("from Country c where c.name='" + name + "'",Country.class).getSingleResult();
		
		System.out.println(dbObject.toString());
		input = false;
		}catch(Exception e) {
			System.out.println(" Invalid Input !");
			
		}
		}while(input);
		
	}
	public boolean isNumeric(String val) {

		try {
			Integer.parseInt(val);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean ischeckChar(String word) {
		
		char[] ch  = word.toCharArray();
		boolean  flag  = true;
		for (int i =0;i<ch.length;i++)
		{
			if(!((ch[i] >= 'a' && ch[i] <= 'z' )||(ch[i]>='A' && ch[i]<='Z')||ch[i]==' ')) {
				
				flag  =  false;
				break;
			}
		}
		if (flag  == false) {
			
			return false;
		}else {
		return true;
		}
		
	}
	
	public  void viewCountry() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("_____View All Available Countries_____");
		stringBuilder.append(String.format("%135s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-50s|%-50s|%-10s|%-10s|\n", "COUNTRY ID","NAME", "LANGUAGE", "CURRENCY","TAX RATE").replace(' ', '_'));
		TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c", Country.class);
		List<Country> results = query.getResultList();		
		for (int i =0;i<results.size();i++) {
			
			stringBuilder.append(String.format("|%-10s|", results.get(i).getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-50s|", results.get(i).getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-50s|", results.get(i).getLanguage()).replace(' ', '_'));
			stringBuilder.append(String.format("%-10s|", results.get(i).getCurrency()).replace(' ', '_'));
			stringBuilder.append(String.format("%-10s|", results.get(i).getTaxRate() ).replace(' ', '_'));

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}
	public void updateCountry() {
		try {
			
		System.out.println("Enter Country Name ");
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
		double text_old  = c.taxRate;
		
		updatable(name_old,language_old,currency_old,text_old,c);
		System.out.println("Successfully Updated");
		em.getTransaction().commit();
		
		}catch(Exception e) {
			System.out.println("Error");
		}
		
		
		
		
	}
	
	private static  void updatable(String name,String language,String currency,double tax,Country co) throws IOException
	{
		
		int  count  =0;
		boolean is_valid  =  true;
		do {
		System.out.println("Please Enter the New Name. if you do not want to Change the Name either leave it blank or type no ");
		String new_name =  r.readLine();
		if (ischeckChar(new_name) ==true) {
		if (new_name.equals(" ")|| new_name.toLowerCase().equals("no")) {
			
			co.setName(name);
			count++;
			is_valid = false;
		}else {
			co.setName(new_name);
			count++;
			is_valid  = false;
		}
		}else {
			System.out.println("Invalid Input!");
		}}while(is_valid);
		
		boolean is_valid_ =true;
		do {
		System.out.println("Please Enter the New Language. if you do not want to Change the Language either leave it blank or type no ");
		String new_language=  r.readLine();
		if(ischeckChar(new_language)==true) {
		if (new_language.equals(" ")|| new_language.toLowerCase().equals("no")) {
			
			co.setLanguage(language);
			is_valid_ =  false;
		}else {
			co.setLanguage(new_language);
			is_valid_ =  false;
		}
		}else {
			System.out.println("Invalid Input");
		}
		}while(is_valid_);
		
		boolean is_valid_input__ =true;
		do {
		System.out.println("Please Enter the New Currency. if you do not want to Change the Currency either leave it blank or type no ");
		String new_curreny =  r.readLine();
		if (new_curreny.length() < 3 || ischeckChar(new_curreny)==true) {
		if (new_curreny.equals(" ") || new_curreny.toLowerCase().equals("no")) {
			
			co.setCurrency(currency);
			is_valid_input__ =  false;
		}else {
			co.setCurrency(new_curreny);
			is_valid_input__   = false;
			
		}}else {
			System.out.println("Invalid Input !");
		}
		}while(is_valid_input__);
		
		boolean  is_valid__input  =  true;
		do {
		System.out.println("Please Enter the New Tax Rate. if you do not want to Change the Tax Rate either leave it blank or type no ");
		String Tax_rate =  r.readLine();
		try {
		if (Tax_rate.equals(" ") || Tax_rate.toLowerCase().equals("no")) {
			
			co.setTaxRate(tax);
			is_valid__input =false;
		}else {
			co.setTaxRate(Double.parseDouble(Tax_rate));
			is_valid__input =false;
		}
		}catch(Exception e) {
			System.out.println("Invalid Tax Rate");
		}
		}while(is_valid__input);
		
		
	}
	
	public void deleteCountry() {
		
		try {
			
			System.out.println("Enter the Name of the Country ! You want to delete");
			String name =  sc.next();
			Query query  = em.createQuery("Select id from Country c where c.name LIKE :name");
			query.setParameter("name", name);
			List<Integer> id   = query.getResultList();
			
			TypedQuery<Location> query1 = em.createQuery("SELECT c FROM Location c where c.countryId = "+id.get(0), Location.class);
			List<Location> results = query1.getResultList();
			
			if(!results.isEmpty())
			{
				System.out.println("Sorry, You can’t Delete this Country because it has Entity in Location Table ");
			}else {
			
			
			em.getTransaction().begin();
			Country c_name = em.find(Country.class, id.get(0));
		    em.remove(c_name);
			em.getTransaction().commit();
			System.out.println("Successfully deleted");
			}
		}catch(Exception e )
		{
			e.printStackTrace();
		
		}
		
	}

	
	public void country(EntityManager em) throws IOException {

    
    	System.out.println("Choose the Field that you want to see, Please Enter the Number only ");
    	System.out.println("1. Add ");
    	System.out.println("2. View ");
    	System.out.println("3. update ");
    	System.out.println("4. Delete ");
    	
    	String  result  =r.readLine();
    	
    	switch(Integer.parseInt(result) ){
    		
    	case 1:
    		addCountry(); break;
    	case 2:
    		viewCountry();break;
    	case 3:
    		updateCountry();break;
    		
    	case 4:
    		deleteCountry();
    	
    		break;
    		
    	default:
    			System.out.println("Error");
    			
    	}
	}
	
	public void department(EntityManager em) {
		
		System.out.println("Choose the Field that you want to go, Please Enter the Number only ");
    	System.out.println("1. Add ");
    	System.out.println("2. View ");
    	System.out.println("3. Update ");
    	System.out.println("4. Delete ");
    	
    	int  result  = sc.nextInt();
    	
    	
	}
	

	public void ask_what(EntityManager em) throws IOException
	{
		
		System.out.println("Please Enter the Number in which you want to do Transaction by looking in the option");
		System.out.println("1. Country ");
		System.out.println("2. Location ");
		System.out.println("3. Department ");
		int number  =  sc.nextInt();
		
		switch(number) {
		
		case 1 : country(em); d_name="Country"; break;
		
		default: System.out.println("Invalid Number. Try again ! ");
		
		}
		
		
		
	}
	public void call(EntityManager em) throws IOException {
		
    	ask_what(em);
    	
    	String answer ;
		do {
    		System.out.println("Do you want to see the Changes in this Table or Go back to Main Menu ? Enter yes or no");
        	answer  = r.readLine();
    
        	if(answer.toLowerCase().equals("yes")) {
        if (DepartmentManager.d_name.equals("Country")) {		
    		country(em);
    		
        }
        	}else {
        	ask_what(em);
        	}
    	}while(answer.toLowerCase() !="");
	}

}
=======
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
>>>>>>> b5fcea1b60652f4fa73817b5b1a84d3006c631a3
