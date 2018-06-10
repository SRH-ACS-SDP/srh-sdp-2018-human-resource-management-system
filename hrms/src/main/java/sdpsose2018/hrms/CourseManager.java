package sdpsose2018.hrms;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CourseManager {

	EntityManager entitymanager;
	List<Course> courses;
	static Scanner scanner;
	
	

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
			
			try {
				System.out.println("Enter course name ");
				String name  = scanner.next();
				Query query =entitymanager.createQuery("Select id from Course c where c.name LIKE :name");
				query.setParameter("name", name);
				
				List id = query.getResultList();
				entitymanager.getTransaction().begin();
				Course c  = entitymanager.find(Course.class,id.get(0));
				
				//Now we need to update the code
				
				String name_old  = c.name;
				String description_old  = c.description;
				String requiredSkills_old   =  c.requiredSkills;
				String acquiredSkills_old  = c.acquiredSkills;
				String mentorSkills_old  = c.mentorSkills;
				
				updatable(name_old,description_old,requiredSkills_old,acquiredSkills_old,mentorSkills_old,c);

				System.out.println("Successfully Updated");
				entitymanager.getTransaction().commit();
				courses = entitymanager.createQuery("from Course", Course.class).getResultList();
				
				}catch(Exception e) {
					System.out.println("Error");
				}
			
		}
		
		private static void updatable(String name,String description,String requiredSkills,String acquiredSkills,String mentorSkills,Course co){
			
			int  count  =0;
			System.out.println("Please Enter the new Course name: ");
			System.out.println("if you do not want to change the Course name either leave it blank or type no: ");
			String new_name =  scanner.next();
			if (new_name.equals(" ")|| new_name.toLowerCase().equals("no")) {
				co.setName(name);
				count++;
			}else {
				co.setName(new_name);
				count++;
			}
			
			System.out.println("Please Enter the new Description: ");
			System.out.println("if you do not want to change the Description either leave it blank or type no: ");
			String new_description=  scanner.next();
			if (new_description.equals(" ")|| new_description.toLowerCase().equals("no")) {
				co.setDescription(description);
			}else {
				co.setDescription(new_description);
			}
			
			
			System.out.println("Please Enter the new Required Skills: ");
			System.out.println("if you do not want to change the Required Skills either leave it blank or type no: ");
			String new_requiredSkills =  scanner.next();
			if (new_requiredSkills.equals(" ") || new_requiredSkills.toLowerCase().equals("no")) {
				co.setRequiredSkills(requiredSkills);
			}else {
				co.setRequiredSkills(new_requiredSkills);
			}
			
			System.out.println("Please Enter the new Acquired Skills: ");
			System.out.println("if you do not want to change the Acquired Skills either leave it blank or type no: ");
			String new_acquiredSkills =  scanner.next();
			if (new_acquiredSkills.equals(" ") || new_acquiredSkills.toLowerCase().equals("no")) {
				co.setAcquiredSkills(acquiredSkills);
			}else {
				co.setAcquiredSkills(new_acquiredSkills);
			}
			
			System.out.println("Please Enter the new Mentor Skills: ");
			System.out.println("if you do not want to change the Mentor Skills either leave it blank or type no: ");
			String new_mentorSkills =  scanner.next();
			if (new_mentorSkills.equals(" ") || new_mentorSkills.toLowerCase().equals("no")) {
				co.setMentorSkills(mentorSkills);
			}else {
				co.setAcquiredSkills(new_mentorSkills);
			}
			
			
		}
		
		
}

