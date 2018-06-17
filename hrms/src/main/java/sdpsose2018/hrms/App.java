package sdpsose2018.hrms;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * Hello world!
 *
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager entityManager = emf.createEntityManager();
    	Scanner scanner = new Scanner(System.in);
    	EmployeeManager employeeManager = new EmployeeManager(entityManager,scanner);
    	DepartmentManager departmentManager = new DepartmentManager(entityManager,scanner);
    	
    	boolean isDone = false;
    	do{
    		System.out.println("Welcome to your personal Human Resource Management System.");
    		System.out.println("Please enter the number of one option below to continue.");
    		System.out.println("1.) Go to the Employee-Management-Module.");
    		System.out.println("2.) Go to the Department-Management-Module.");
			System.out.println("3.) Exit the application.");
			
			try {
				int input = Integer.parseInt(scanner.nextLine());
				switch(input) {
				case 1:
					employeeManager.start();
					break;
				case 2:
					departmentManager.call();
					break;
				case 3:
					System.out.println("Thanks for using this Human Resource Management System.");
					System.out.println("Bye.");
					scanner.nextLine();
					isDone = true;
					break;
				default:
					System.out.println("Input is invalid.");
					break;
				}
				
			} catch (NumberFormatException nfe) {
				System.out.println("Input must be a natural number below " + Integer.MAX_VALUE + ".");
			}
    	} while(!isDone);
    	
		entityManager.close();
    	scanner.close();
    }
}