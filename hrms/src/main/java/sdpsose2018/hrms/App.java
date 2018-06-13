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
    public static void main( String[] args )
    {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
    	EntityManager em = emf.createEntityManager();
    	
    	Scanner sc = new Scanner(System.in);
    	
    	//ManageCourse mc = new ManageCourse(em,sc);
    	ManageTraining mt = new ManageTraining(em,sc);
    	mt.addTraining();
    	//mc.addCourse();
    	//mc.viewCourse();
    }
}
