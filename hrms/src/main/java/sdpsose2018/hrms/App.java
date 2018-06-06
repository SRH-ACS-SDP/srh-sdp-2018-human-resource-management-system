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
	
	public void jpaProcess() {

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
	
    public static void main( String[] args )
    {
    	App app = new App();
    	app.jpaProcess();
    	
    	String answer ;
    	do {
    		System.out.println("Do you want to see the changes");
        	answer  = sc.next();
    	
        	if(answer.toLowerCase().equals("yes")) {
    		app.jpaProcess();
        	}
    	}while(answer.toLowerCase() !="");
    			
    }
    
    	
    	
    	
    }

