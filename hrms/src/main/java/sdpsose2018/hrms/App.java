package sdpsose2018.hrms;

import java.io.IOException;
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
    	EntityManager em = emf.createEntityManager();
    	Scanner sc = new Scanner(System.in);
    	
    	EmployeeManager employeeManager = new EmployeeManager(em,sc);
    	employeeManager.viewEmployees();
    	
    	sc.close();
    	em.close();
    }
}