package sdpsose2018.hrms;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

public class ManageCourse {

	EntityManager em;
	List<Course> courses;
	Scanner sc;
	
	public ManageCourse(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
		courses = em.createQuery("from Course", Course.class).getResultList();
	}
	
	public void addCourse() {

		Course co = new Course();

		System.out.print("Course name: ");
		String coursename = sc.nextLine();
		co.setName(coursename);
	
		System.out.print("Acquired Skills: ");
		String AS = sc.nextLine();
		co.setAcquiredSkills(AS);
	
		System.out.print("Description: ");
		String De = sc.nextLine();
		co.setDescription(De);
	
		System.out.print("Mentor Skills: ");
		String MS = sc.nextLine();
		co.setMentorSkills(MS);
	
		System.out.print("Required Skills: ");
		String RS = sc.nextLine();
		co.setRequiredSkills(RS);
	
		em.getTransaction().begin();
		em.persist(co);
		em.getTransaction().commit();
	
		
		
		Course dbObject = em.createQuery("from Course c where c.name='" + coursename + "'", Course.class).getSingleResult();
		
		System.out.println(dbObject);
		
		courses = em.createQuery("from Course", Course.class).getResultList();

}
	
	//ViewCourseFunction
	
	public void viewCourse() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Available Courses\n");
		stringBuilder.append(String.format("%142s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "COURSE ID","NAME", "DESCRIPTION", "REQUIRED SKILLS", "ACQUIRED SKILLS", "MENTOR SKILLS").replace(' ', '_'));
		
		for (Course c : courses) {
			
			stringBuilder.append(String.format("|%10d|", c.getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getDescription()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getRequiredSkills()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getAcquiredSkills()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", c.getMentorSkills()).replace(' ', '_'));
			
			
			
			//stringBuilder.append(c.toString());
			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}

		public void updateCourse() {
			
			
			
		}
}

