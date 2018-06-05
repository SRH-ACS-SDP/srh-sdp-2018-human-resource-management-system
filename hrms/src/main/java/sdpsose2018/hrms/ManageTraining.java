package sdpsose2018.hrms;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManageTraining {

	EntityManager em;
	List<Training> trainig;
	Scanner sc;
	
	public ManageTraining (EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;

		trainig = em.createQuery("from Training", Training.class).getResultList();
	}
	
	public void addTraining() {
		
		Training tr = new Training();
		ManageCourse mc = new ManageCourse(em, sc);
		
		//Add Course ID
		System.out.print("Course ID: ");
		System.out.print("Do you want to view Courses?(y/n) ");
		char c = sc.nextLine().charAt(0);
		
		if (c == 'y') {
			
			mc.viewCourse();
			System.out.print("Course is exist? (y/n)");
			c = sc.nextLine().charAt(0);
				if (c == 'y') {
					System.out.print("Enter Course ID: ");
					int courseid = Integer.parseInt(sc.nextLine());
					tr.setCourseId(courseid);
				
				}else if(c == 'n'){
					mc.addCourse();
					mc.viewCourse();
					System.out.print("Enter Course ID: ");
					int courseid = Integer.parseInt(sc.nextLine());
					tr.setCourseId(courseid);
				}
	
		}else if (c == 'n') {
			System.out.print("Enter Course ID: ");
			int courseid = Integer.parseInt(sc.nextLine());
			tr.setCourseId(courseid);
			
		}else {
			System.out.print("Enter valid input");
			addTraining();
		}

		//Add Conductor ID
				System.out.print("Conductor ID: ");
				System.out.print("Are you going to conduct the Training(y/n) ");
				c = sc.nextLine().charAt(0);
				
				if (c == 'y') {
					
					System.out.print("Enter your ID: ");
					int conductorId = Integer.parseInt(sc.nextLine());
					tr.setConductorId(conductorId);
					
				}else if (c == 'n') {
					System.out.print("Enter Conductor ID: ");
					System.out.print("Do you want to view employees?(y/n) ");
					c = sc.nextLine().charAt(0);
					
					if (c == 'y') {
						
						//Call: ViewEmployee();
						System.out.print("Enter Conductor ID: ");
						int conductorId = Integer.parseInt(sc.nextLine());
						tr.setConductorId(conductorId);
						
					}else if (c == 'n') {
						System.out.print("Enter Conductor ID: ");
						int conductorId = Integer.parseInt(sc.nextLine());
						tr.setConductorId(conductorId);
					
				}else {
					System.out.print("Enter valid input");
					addTraining();
				}
			
				}
					
		em.getTransaction().begin();
		em.persist(tr);
		em.getTransaction().commit();
		
		
	
				
}
}
