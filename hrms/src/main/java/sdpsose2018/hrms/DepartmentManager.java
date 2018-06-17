package sdpsose2018.hrms;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;





public class DepartmentManager {

	List<Country> countries;
	List<Location> location;
	List<Department> departments;
	public static String d_name ="";
	
	EntityManager em;

     Scanner sc;
	
	
	

	public DepartmentManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
		countries = em.createQuery("from Country", Country.class).getResultList();
		location  =  em.createQuery("from Location",Location.class).getResultList();
		departments  = em.createQuery("from Department",Department.class).getResultList();
		
	}
	
	public void addCountry() {
		Country co = new Country();
		
		boolean invalidInput = true;
		String name;
		
		do {
			System.out.println("Add Country Details");
			System.out.print("Country Name: ");
			System.out.print("=>");
			name = sc.nextLine();
			if(ischeckChar(name)==true) {
			co.setName(name);
	    	try {
	    		em.createQuery("from Country where name = '" + name + "'").getSingleResult();    		
	    		System.out.println("Country already exists in database.");
	    		boolean isRetry = true;
	    		do {
		    		System.out.print("Try Again.? (y/n) ");
		    		System.out.print("=>");
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
				System.out.println("Wrong input!");
			}
		} while (invalidInput);
		
		boolean input_type_ = true;
		do {
		System.out.print("Country Language: ");
		System.out.print("=>");
		String language = sc.nextLine();
		if(ischeckChar(language)==false) {
			System.out.println("Enter only strings");
		}else {
		co.setLanguage(language);
		input_type_ = false;
		}
		}while(input_type_);
		
		boolean input_type = true;
		do {
		
		System.out.print("Country Currency: like [USD,EUR,INR,RUS] etc ");
		System.out.print("=>");
		String currency = sc.nextLine();	
		
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
		System.out.print("=>");
		String  tax  =  sc.nextLine();
		co.setTaxRate(Double.parseDouble(tax));
		
		em.getTransaction().begin();
		em.persist(co);
		em.flush();
		em.clear();
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
		System.out.print("=>");
		String name  = sc.nextLine();
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
		System.out.println("Successfully updated");
		em.flush();
		em.clear();
		em.getTransaction().commit();
		
		}catch(Exception e) {
			System.out.println("Error");
		}
		
		
		
		
	}
	
	private void updatable(String name,String language,String currency,double tax,Country co)
	{
		boolean is_valid  =  false;
		do {
			System.out.println("Please Enter the new name. If you do not want to change the name either leave it blank or type no ");
			System.out.print("=>");
			String new_name =  sc.nextLine();
			if (ischeckChar(new_name) == true)
			{
				if (new_name.equals(" ")|| new_name.equals("no")|| new_name.equals(""))
				{
					co.setName(name);
					is_valid = true;
				}else {
					try {
			    		em.createQuery("from Country where name = '" + new_name + "'").getSingleResult();    		
			    		System.out.println("Country already exists in Database.");
			    		boolean isRetry = true;
			    		do {
				    		System.out.print("Try Again.? (y/n) ");
				    		System.out.print("=>");
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
			    		co.setName(new_name);
			    		is_valid = true;
			    	} catch (Exception e){
			    		System.out.println(e.getMessage());
			    		is_valid  = false;
			    	}
				}
			}else {
				System.out.println("Invalid input");
			}
		} while(!is_valid);
			
		
		boolean is_valid_ =true;
		do {
		System.out.println("Please Enter the new Language. If you do not want to change the language either leave it blank or type no ");
		System.out.print("=>");
		String new_language=  sc.nextLine();
		if(ischeckChar(new_language)==true) {
		if (new_language.equals(" ")|| new_language.equals("no") || new_language.equals("")) {
			
			co.setLanguage(language);
			is_valid_ =  false;
		}else {
			co.setLanguage(new_language);
			is_valid_ =  false;
		}
		}else {
			System.out.println("Invalid input");
		}
		}while(is_valid_);
		
		boolean is_valid_input__ =true;
		do {
		System.out.println("Please Enter the new currency. If you do not want to change the currency either leave it blank or type no ");
		System.out.print("=>");
		String new_curreny =  sc.nextLine();
		if (new_curreny.length() <= 3 && ischeckChar(new_curreny)==true) {
		if (new_curreny.equals(" ") || new_curreny.equals("no") || new_curreny.equals("")) {
			
			co.setCurrency(currency);
			is_valid_input__ =  false;
		}else {
			co.setCurrency(new_curreny);
			is_valid_input__   = false;
			
		}}else {
			System.out.println("Invalid input ");
		}
		}while(is_valid_input__);
		
		boolean  is_valid__input  =  true;
		do {
			
		System.out.println("Please Enter the new tax rate. If you do not want to change the tax rate either leave it blank or type no ");
		System.out.print("=>");
		String Tax_rate = sc.nextLine().toLowerCase();
		try {
			
		if (Tax_rate.equals(" ") || Tax_rate.equals("no") || Tax_rate.equals("")) {
			
			co.setTaxRate(tax);
			is_valid__input =false;
		}else {
			co.setTaxRate(Double.parseDouble(Tax_rate));
			is_valid__input =false;
		}
		}catch(Exception e) {
			System.out.println("Invalid input");
		}
		}while(is_valid__input);
		
		
	}
	
	public void deleteCountry() {
		
		try {
			
			System.out.println("Enter the name of the Country ! You want to delete.");
			System.out.print("=>");
			String name =  sc.nextLine();
			Query query  = em.createQuery("Select id from Country c where c.name LIKE :name");
			query.setParameter("name", name);
			
			List<Integer> id   = query.getResultList();
			
			TypedQuery<Location> query1 = em.createQuery("SELECT c FROM Location c where c.countryId = "+id.get(0), Location.class);
			List<Location> results = query1.getResultList();
			
			if(!results.isEmpty())
			{
				System.out.println("Sorry, You can’t delete this country because it has entity in location table. ");
			}else {
			
			
			em.getTransaction().begin();
			Country c_name = em.find(Country.class, id.get(0));
		    em.remove(c_name);
		    em.flush();
			em.clear();
			em.getTransaction().commit();
			System.out.println("Successfully deleted.");
			}
		}catch(Exception e )
		{
			e.printStackTrace();
		
		}
		
	}

	public void addLocation() {
		try {
			
			TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c", Country.class);
			List<Country> results = query.getResultList();
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("\t ******List of Country******");
			System.out.println("ID"+"  "+"Name");
			for(int i =0;i<results.size();i++)
			{
				System.out.println(results.get(i).id+"   "+results.get(i).name +"");
				map.put("id"+i, results.get(i).id);
				
			}
			
			System.out.println("Does the Country exists ? Enter yes or no .");
			System.out.print("=>");
			String answer  =sc.nextLine();
			
			
			if(answer.toLowerCase().equals("no")) {
				addCountry();
				addLocation();
			}else if(answer.toLowerCase().equals("yes")) {
			System.out.println("Select the Country ID.");
			System.out.print("=>");
			
			int number  =  Integer.parseInt( sc.nextLine());
			if(!map.containsValue(number))
			{
				System.out.println("Invalid input");
				
			}else {
			
			//
			Location co = new Location();
			
			boolean invalidInput = true;
			String name;
			
			do {
				System.out.println("Add Location Details");
				System.out.print("Location Name : ");
				System.out.print("=>");
				name =sc.nextLine();
				if  (ischeckChar(name) == true) {
				co.setName(name);
		    	try {
		    		em.createQuery("from Location where name = '" + name + "'").getSingleResult();    		
		    		System.out.println("Location already exists in database.");
		    		boolean isRetry = true;
		    		do {
			    		System.out.print("Try again? (y/n) ");
			    		System.out.print("=>");
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
					System.out.println("Invalid input ");
					System.out.print("=>");
				}
			} while (invalidInput);
			
			
			
			System.out.print("Location Address: ");
			System.out.print("=>");
			String l_address = sc.nextLine();
			co.setAddress(l_address);
			
			
		
			System.out.print("Location Details: ");
			System.out.print("=>");
			String l_detailes = sc.nextLine();
			co.setDetails(l_detailes);
			
			co.setCountryId(number);
			em.getTransaction().begin();
			em.persist(co);
			em.flush();
			em.clear();
			em.getTransaction().commit();
		
			Location dbObject = em.createQuery("from Location c where c.name='" + name + "'",Location.class).getSingleResult();
			
			System.out.println(dbObject.toString());
			
			
			}
			
			
			}
			  
			
		}catch(Exception e )
		{
			e.printStackTrace();
		}
	}
	public void viewLocation() {
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("_____View All Available Location______");
		stringBuilder.append(String.format("%150s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-15s|%-40s|%-40s|%-40s|%-10s|\n", "LOCATION ID","NAME", "ADDRESS", "DETAILS","COUNTRY ID").replace(' ', '_'));
		TypedQuery<Location> query = em.createQuery("SELECT c FROM Location c", Location.class);
		List<Location> results = query.getResultList();		
		for (int i =0;i<results.size();i++) {	
			stringBuilder.append(String.format("|%-15s|", results.get(i).getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|", results.get(i).getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|",  results.get(i).getAddress()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|",  results.get(i).getDetails()).replace(' ', '_'));
			stringBuilder.append(String.format("%-10s|",  results.get(i).getCountryId()).replace(' ', '_'));

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}
	public void updateLocation() {
		
		try {
			
			TypedQuery<Location> query = em.createQuery("SELECT c FROM Location c", Location.class);
			List<Location> results = query.getResultList();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("\t ******List of Location******");
			System.out.println("ID"+"             "+"Name");
			for (int i =0;i<results.size();i++) {
				System.out.println(results.get(i).id+"             "+results.get(i).name);
				map.put("id"+i, results.get(i).id);
			}
			
			System.out.println("Select the location ID ");
			System.out.print("=>");
			int number  =  Integer.parseInt( sc.nextLine());
			
			if(!map.containsValue(number))
			{
				System.out.println("Invalid Input");
				
			}else {
				
				em.getTransaction().begin();
				Location c  = em.find(Location.class,number);
				
				String name_old  =  c.name;
				String detailes_old  = c.details;
				String address_old  =  c.address;
				
				updatablelocation(name_old,detailes_old,address_old,c);
				System.out.println("Successfully Updated");
				em.flush();
				em.clear();
				em.getTransaction().commit();
				
			}
			
			
			}catch(Exception e) {
				e.printStackTrace();
			}
		
	}
	
	public void updatablelocation(String name,String detailes,String address,Location co) 
	{
		boolean  is_valid  = true;
		do {
		System.out.println("Please Enter the new name. If you do not want to change the name either leave it blank or type no .");
		System.out.print("=>");
		String new_name = sc.nextLine();
		if(ischeckChar(new_name) ==true) {
		if (new_name.equals(" ")|| new_name.toLowerCase().equals("no")|| new_name.equals("")) {
			
			co.setName(name);
			is_valid  =  false;
			
		}else {
			co.setName(new_name);
			is_valid  = false;
			
		}
		}else {
			System.out.println("Invalid !");
		}
		}while(is_valid);
		
		System.out.println("Please Enter the new details. If you do not want to change the details either leave it blank or type no .");
		System.out.print("=>");
		String new_details= sc.nextLine();
		if (new_details.equals(" ")|| new_details.toLowerCase().equals("no")|| new_details.equals("")) {
			
			co.setDetails(detailes);
		}else {
			co.setDetails(new_details);
		}
		
		
		System.out.println("Please Enter the new address. if you do not want to change the address either leave it blank or type no .");
		System.out.print("=>");
		String new_address = sc.nextLine();
		if (new_address.equals(" ") || new_address.toLowerCase().equals("no") || new_address.equals("")) {
			
			co.setAddress(address);
		}else {
			co.setAddress(new_address);
		}
		
	}
	
	
	public void deleteLocation() {
		
		try {
			
			TypedQuery<Location> query = em.createQuery("SELECT c FROM Location c", Location.class);
			List<Location> results_location = query.getResultList();
			
			
			System.out.println("\t*******List Of Location with following Countries**********");
			
			System.out.println("ID    "+"       Location Name           "+"         Country          ");
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for (int  i =0;i<results_location.size();i++) {
				int c_id  =  results_location.get(i).countryId;
				Query query_Country =  em.createQuery("select c from Country c where c.id="+c_id,Country.class);
				List<Country> name = query_Country.getResultList();
				map.put("id"+i, results_location.get(i).id);
				System.out.println(results_location.get(i).id+"         "+results_location.get(i).name+"            \t"+name.get(0).name);
			
			}
			
			System.out.println("Please Enter the Location Id. If you want to delete");
			System.out.print("=>");
			int number  =  Integer.parseInt( sc.nextLine());
			
			if(!map.containsValue(number)) {
				System.out.println("Invalid input");
				
			}else {
				
				TypedQuery<Department> query1 = em.createQuery("SELECT c FROM Department c where c.locationId = "+number, Department.class);
				List<Department> results = query1.getResultList();
				
				if(!results.isEmpty())
				{
					System.out.println("Sorry, You can't Delete this Location because it has entity in department table ");
				
				
				}else {
					em.getTransaction().begin();
					Location L_name = em.find(Location.class,number);
					em.remove(L_name);
					em.flush();
					em.clear();
					em.getTransaction().commit();
					System.out.println("Successfully deleted");
					
				}
}
		}catch(Exception e )
		{
			e.printStackTrace();	
		}		
}
	public void addDepartment() {
		try {
			
			TypedQuery<Location> query = em.createQuery("SELECT c FROM Location c", Location.class);
			List<Location> results = query.getResultList();
			
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			System.out.println("\t*****Location Details*******");
			System.out.println(" Location Id  "+"  "+" Location Name ");
			for(int i =0;i<results.size();i++)
			{
				System.out.println(results.get(i).id+"  "+results.get(i).name +"");
				map.put("id"+i, results.get(i).id);
				
			}
			System.out.println("Does the Location exists ? Enter yes or no");
			String answer  =sc.nextLine();
			if(answer.toLowerCase().equals("no")) {
				addLocation();
			}else if (answer.toLowerCase().equals("yes"));
			System.out.println("Select the Location Id");
			System.out.print("=>");
			int number =  Integer.parseInt(sc.nextLine());
			if(!map.containsValue(number)) {
				System.out.println("Wrong Input");
			}else {
				
				Department co = new Department();
				
				boolean invalidInput = true;
				String name;
				
				do {
					System.out.println("Add Department Details");
					System.out.print("Department Name : ");
					System.out.print("=>");
					name = sc.nextLine();
					if(ischeckChar(name) ==true) {
					co.setName(name);
			    	try {
			    		em.createQuery("from Department where name = '" + name + "'").getSingleResult();    		
			    		System.out.println(" Department already exists in database. ");
			    		boolean isRetry = true;
			    		do {
				    		System.out.print("Try again? (y/n) ");
				    		System.out.print("=>");
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
						System.out.println("Invalid input");
					}
				} while (invalidInput);
				
				System.out.print(" Department Address: ");
				System.out.print("=>");
				String l_address = sc.nextLine();
				co.setAddress(l_address);
				
				
			
				System.out.print(" Department Description: ");
				System.out.print("=>");
				String l_description = sc.nextLine();
				co.setDescription(l_description);
				
				co.setLocationId(number);
				em.getTransaction().begin();
				em.persist(co);
				em.flush();
				em.clear();
				em.getTransaction().commit();
			
				Department dbObject = em.createQuery("from Department c where c.name='" + name + "'",Department.class).getSingleResult();
				
				System.out.println(dbObject.toString());
				
				
				}
		}catch(Exception e ) {
			
			e.printStackTrace();
		}
	}
	
	public void viewDepartment() {

		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("_____View All Available Departments______");
		stringBuilder.append(String.format("%155s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-15s|%-40s|%-40s|%-40s|%-15s|\n", "DEPARTMENT ID","NAME", "ADDRESS", "DESCRIPTION","LOCATION ID").replace(' ', '_'));
		
		TypedQuery<Department> query = em.createQuery("SELECT c FROM Department c", Department.class);
		List<Department> results = query.getResultList();		
		for (int i =0;i<results.size();i++) {
			
			stringBuilder.append(String.format("|%-15s|", results.get(i).getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|", results.get(i).getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|", results.get(i).getAddress()).replace(' ', '_'));
			stringBuilder.append(String.format("%-40s|", results.get(i).getDescription()).replace(' ', '_'));
			stringBuilder.append(String.format("%-15s|", results.get(i).getLocationId()).replace(' ', '_'));

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}
	
	public void updateDepartment() {
		
		try {
					
					TypedQuery<Department> query = em.createQuery("SELECT c FROM Department c", Department.class);
					List<Department> results = query.getResultList();
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					System.out.println("\t ******list of Department******");
					System.out.println("ID"+"   "+"Name");
					for (int i =0;i<results.size();i++) {
						System.out.println(results.get(i).id+" -- "+results.get(i).name);
						map.put("id"+i, results.get(i).id);
					}
					
					System.out.println("Select the ID ");
					System.out.print("=>");
					int number  =  Integer.parseInt(sc.nextLine());
					if(!map.containsValue(number))
					{
						System.out.println("Invalid Input");
						System.out.print("=>");
					}else {
						
						em.getTransaction().begin();
						Department c  = em.find(Department.class,number);
						
						String name_old  =  c.name;
						String description_old  = c.description;
						String address_old  =  c.address;
						
						updatabledepartment(name_old,description_old,address_old,c);
						System.out.println("Successfully Updated");
						em.flush();
						em.clear();
						em.getTransaction().commit();
						
					}
					
					
					}catch(Exception e) {
						e.printStackTrace();
					}
			}
			
	
	
	
	private void updatabledepartment(String name,String description,String address,Department co) 
	{
		
		boolean  is_valid  = true;
		
		do {
		System.out.println("Please enter the new name. If you do not want to change the name either leave it blank or type no ");
		System.out.print("=>");
		String new_name = sc.nextLine();
		if(ischeckChar(new_name)==true) {
		if (new_name.equals(" ")|| new_name.toLowerCase().equals("no")||(new_name.equals(""))) {
			
			co.setName(name);
			is_valid  = false;
			
		}else {
			co.setName(new_name);
			is_valid = false;
			
		}
		
		}else {
			System.out.println("Invalid input");
		}
		}while(is_valid);
		
		
		System.out.println("Please Enter the new description. If you do not want to change the description either leave it blank or type no ");
		System.out.print("=>");
		String new_description= sc.nextLine();
		if (new_description.equals(" ")|| new_description.toLowerCase().equals("no")|| (new_description.equals(""))) {
			
			co.setDescription(description);
		}else {
			co.setDescription(new_description);
		}
		
		
		System.out.println("Please Enter the new address. If you don't  want to change the address either leave it blank or type no ");
		System.out.print("=>");
		String new_address = sc.nextLine();
		if (new_address.equals(" ") || new_address.toLowerCase().equals("no")|| (new_address.equals(""))) {
			
			co.setAddress(address);
		}else {
			co.setAddress(new_address);
		}
}
	
	public void delete_department() {
		try {
					
					TypedQuery<Department> query = em.createQuery("SELECT c FROM Department c", Department.class);
					List<Department> results_department = query.getResultList();
					
					
					System.out.println("\t*******List Of Location with following countries**********");
					
					System.out.println("ID  "+"Department Name   "+"Country");
					
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					for (int  i =0;i<results_department.size();i++) {
						int c_id  =  results_department.get(i).locationId;
						Query query_Department =  em.createQuery("select c from Location c where c.id="+c_id,Location.class);
						
						@SuppressWarnings("unchecked")
						List<Location> name_location = query_Department.getResultList();
						
						map.put("id"+i, results_department.get(i).id);
						System.out.println(results_department.get(i).id+"   "+results_department.get(i).name+"  \t"+name_location.get(0).name);
						
					}
					
					System.out.println("Select the ID");
					System.out.print("=>");
					int number = Integer.parseInt(sc.nextLine());
					
					if(!map.containsValue(number)) {
						System.out.println("Invalid Input ):");
						
					}else {
						 
						em.getTransaction().begin();
						Department L_name = em.find(Department.class,number);
						em.remove(L_name);
						em.flush();
						em.clear();		
						em.getTransaction().commit();
						System.out.println("Successfully Deleted");
						
						
					}
					
			}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void department() {
			
					System.out.println("- Manage Departments -  ");
					System.out.println("Please enter the number of one option below to continue.");
					System.out.println("1. Add department ");
					System.out.println("2. View department");
					System.out.println("3. Update department");
					System.out.println("4. Delete department");
					System.out.print("=>");
    	
					int  result  = Integer.parseInt(sc.nextLine());
    	
					switch(result) {
    		
					case 1:
						addDepartment(); 
						break;
					case 2:
						viewDepartment();
						break;
					case 3:
						updateDepartment();
						break;
					case 4:
						delete_department();
						break;
					default:
						System.out.println("Error");
    			
    	     }
	}
				

	public void location() {
					
					System.out.println("- Manage Locations -  ");
					System.out.println("Please enter the number of one option below to continue.");
					System.out.println("1. Add location ");
					System.out.println("2. View location ");
					System.out.println("3. Update location");
					System.out.println("4. Delete location");
					System.out.print("=>");
					int  result  = Integer.parseInt(sc.nextLine());
    	
					switch(result) {
    		
					case 1:
						addLocation(); 
						break;
					case 2:
						viewLocation();
						break;
					case 3:
						updateLocation();
						break;
					case 4:
						deleteLocation();
						break;
					default:
						System.out.println("Error");
   		
				}
	}
	
	public void country() {

    
    				System.out.println(" - Manage Countries - ");
    				System.out.println("Please enter the number of one option below to continue.");
    				System.out.println("1. Add countries ");
    				System.out.println("2. View countries ");
    				System.out.println("3. Update countries ");
    				System.out.println("4. Delete countries ");
    				System.out.print("=>");
    	
    				String  result  = sc.nextLine();
    	
    				switch(Integer.parseInt(result) ){
    		
    				case 1:
    					addCountry();
    					break;
    				case 2:
    					viewCountry();
    					break;
    				case 3:
    					updateCountry();
    					break;
    				case 4:
    					deleteCountry();
    					break;
    				default:
    					System.out.println("Error");
    			
    			}
	}
	
    
	public void start(){
		
				System.out.println("Please enter the number of one option below to continue");
				System.out.println("1. Country ");
				System.out.println("2. Location ");
				System.out.println("3. Department ");
				System.out.println("4. Go back to main menu.");
				System.out.print("=>");
				int number  =  Integer.parseInt(sc.nextLine());
		
				switch(number) {
		
				case 1 : country();
							d_name="Country";
							break;
				case 2 : location(); 
							d_name="Location";
							break;
				case 3 : department();
							d_name="Department";
							break;
				case 4 : d_name="Stop";
							break;
				default:
						System.out.println("Invalid Input. Try again ! ");
		
		}
		
	}
	public void call(){
		
			start();
			
			if(DepartmentManager.d_name.equals("Stop")) {
				return;
			}
    	
			String answer ;
			do {
				System.out.println("Do you want to see the changes in this table? Enter yes or no");
				System.out.print("=>");
					answer  = sc.nextLine();
    
					if(answer.toLowerCase().equals("yes")) {
						if (DepartmentManager.d_name.equals("Country")) {		
							country();
    		
						}else if (DepartmentManager.d_name.equals("Location")) {
							location();
						}else if (DepartmentManager.d_name.equals("Department")) {
							department();
						}
						} else {
						start();
						if(DepartmentManager.d_name.equals("Stop")) {
							return;
						}
						}
				 }while(answer.toLowerCase() !="");
		}

    }
