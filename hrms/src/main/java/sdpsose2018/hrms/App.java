package sdpsose2018.hrms;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Hello world!
 *
 */
public class App 
{
	static Scanner sc;
	
	public App() {
		sc = new Scanner(System.in);
	}
	
	public void country() {

    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager em = emf.createEntityManager();

    	DepartmentManager departmentManager = new DepartmentManager(em, sc);
    	
    	System.out.println("Choose the Field that you want to see, Please Enter the number only ");
    	System.out.println("1 Add");
    	System.out.println("2 View");
    	System.out.println("3 update");
    	System.out.println("4 Delete");
    	
    	int  result  = sc.nextInt();
    	
    	switch(result) {
    		
    	case 1:
    		departmentManager.addCountry(); break;
    	case 2:
    		departmentManager.viewCountry();break;
    	case 3:
    		departmentManager.updateCountry();break;
    		
    	case 4:
    		departmentManager.deleteCountry();
    	
    		break;
    		
    	default:
    			System.out.println("Error");
    	
    	
    			
    	}

    	
    	em.close();
	}
	
	public void location() {

    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager em = emf.createEntityManager();
    	
    	
    	
    	DepartmentManager departmentManager = new DepartmentManager(em, sc);
    	
    	System.out.println("Choose the Field that you want to see, Please Enter the number only ");
    	System.out.println("1 Add");
    	System.out.println("2 View");
    	System.out.println("3 update");
    	System.out.println("4 Delete");
    	
    	int  result  = sc.nextInt();
    	
    	switch(result) {
    		
    	case 1:
    		departmentManager.addLocation(); break;
    	case 2:
    		departmentManager.viewLocation();break;
    	case 3:
    		departmentManager.updateCountry();break;
    		
    	case 4:
    		departmentManager.deleteCountry();
    	
    		break;
    		
    	default:
    			System.out.println("Error");
    	
    	
    			
    	}

    	
    	em.close();
	}
	
	public void ask_what()
	{
		
		System.out.println("Please Enter the number in which you want to do transaction by looking in the option");
		System.out.println("1 Country");
		System.out.println("2 Location");
		System.out.println("3 Department");
		int number  =  sc.nextInt();
		
		switch(number) {
		
		case 1 : country(); DepartmentManager.d_name="Country"; break;
		case 2:  location();DepartmentManager.d_name="Location";break;
		case 3:  DepartmentManager.d_name="Department";break;
		default: System.out.println("Invalid Input Try again ! ");
		
		}
		
		
		
	}
    public static void main( String[] args )
    {
    	App app = new App();
    	app.ask_what();
    	
    	String answer ;
    	do {
    		System.out.println("Do you want to see the changes in this table or Go back to main menu ? enter yes or no");
        	answer  = sc.next();
    	
        	if(answer.toLowerCase().equals("yes")) {
        if (DepartmentManager.d_name.equals("Country")) {		
    		app.country();
        }else if(DepartmentManager.d_name.equals("Location")) {
        	app.location();
        }
        	}else {
        	app.ask_what();
        	}
    	}while(answer.toLowerCase() !="");
    			
    }
    
    	
    	
    	
    }

