package sdpsose2018.hrms;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CourseManager {

	EntityManager entitymanager;
	List<Course> courses;
	Scanner scanner;
	
	

	public CourseManager(EntityManager em, Scanner sc) {
		this.entitymanager = em;
		this.scanner = sc;
		
		
		courses = em.createQuery("from Course", Course.class).getResultList();
	}
	
	public void addCourse() {

		Course co = new Course();
		System.out.println("_____Adding a new Course_____");
		System.out.println();
		boolean invalidInput = true; 
		String coursename;
		
		do {
			
		System.out.print("Course name: ");
		coursename = scanner.nextLine();
		co.setName(coursename);
	
		try {
			entitymanager.createQuery("from Course where name = '"+ coursename +"'").getSingleResult();
			System.out.println("Course already exists in Database.");
    		boolean isRetry = true;
			do {
	    		System.out.print("Try again? (y/n) ");
	    		switch(scanner.nextLine().charAt(0)) {
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
			
		}catch (NoResultException nre) {
    		invalidInput = false;
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    	}
		}while (invalidInput);
		
		System.out.print("Acquired Skills: ");
		String AS = scanner.nextLine();
		co.setAcquiredSkills(AS);
	
		System.out.print("Description: ");
		String De = scanner.nextLine();
		co.setDescription(De);
	
		System.out.print("Mentor Skills: ");
		String MS = scanner.nextLine();
		co.setMentorSkills(MS);
	
		System.out.print("Required Skills: ");
		String RS = scanner.nextLine();
		co.setRequiredSkills(RS);
	
		entitymanager.getTransaction().begin();
		entitymanager.persist(co);
		entitymanager.getTransaction().commit();
	
		
		
		//Course dbObject = em.createQuery("from Course c where c.name='" + coursename + "'", Course.class).getSingleResult();
		//Course dbObject = entitymanager.createQuery("from Course c where c.name='" + coursename + "'",Course.class).getSingleResult();
		//System.out.println(dbObject);
		
		courses = entitymanager.createQuery("from Course", Course.class).getResultList();

}
	
	//ViewCourseFunction
	
	public void viewCourse() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("_____View all available Courses_____");
		stringBuilder.append(String.format("%142s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "COURSE ID","NAME", "DESCRIPTION", "REQUIRED SKILLS", "ACQUIRED SKILLS", "MENTOR SKILLS").replace(' ', '_'));
		
		for (Course c : courses) {
			
			stringBuilder.append(String.format("|%10d|", c.getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getDescription()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getRequiredSkills()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getAcquiredSkills()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getMentorSkills()).replace(' ', '_'));

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}

		public void updateCourse() {
			
			
			
		}
}

