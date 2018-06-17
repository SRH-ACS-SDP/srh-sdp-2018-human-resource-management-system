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
    	Scanner scanner = new Scanner(System.in);
    	EmployeeManager employeeManager = new EmployeeManager(DatabaseConnection.getInstance(),scanner);
    	DepartmentManager departmentManager = new DepartmentManager(DatabaseConnection.getInstance(),scanner);
    	TrainingManager trainingManager = new TrainingManager(DatabaseConnection.getInstance(),scanner);
    	
    	boolean isDone = false;
    	do {
    		System.out.println("Welcome to your personal Human Resource Management System.");
    		System.out.println("**********************************************************");
    		System.out.println("\nPlease enter a number of one option below to continue....\n");
    		System.out.println("1.) Go to the Employee-Management-Module.");
    		System.out.println("2.) Go to the Department-Management-Module.");
    		System.out.println("3.) Go to the Training-Management-Module.");
			System.out.println("4.) Exit the application.");
			System.out.print("\nEnter Number: ");
			
			try {
				int input = Integer.parseInt(scanner.nextLine());
				System.out.println("**********************************************************");
				switch(input) {
				case 1:
					employeeManager.start();
					break;
				case 2:
					departmentManager.call();
					break;
				case 3:
					trainingManager.menuForTrainingManager();
					break;
				case 4:
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
    	
    	DatabaseConnection.getInstance().finalize();
    	scanner.close();
    	
    }
}