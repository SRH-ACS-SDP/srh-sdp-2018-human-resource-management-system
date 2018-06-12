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
		System.out.println("Course has been added successfully");
		entitymanager.getTransaction().commit();
		
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
			String name  = scanner.nextLine();
			entitymanager.getTransaction().begin();
			
			Query query =entitymanager.createQuery("Select id from Course c where c.name LIKE :name");
			query.setParameter("name", name);
			
			List ids = query.getResultList();
			for (Object object : ids) {
				System.out.println("ID : "+object.toString());
			}
			
			Course c  = entitymanager.find(Course.class,ids.get(0));
			
			String name_old  = c.name;
			String description_old  = c.description;
			String requiredSkills_old   =  c.requiredSkills;
			String acquiredSkills_old  = c.acquiredSkills;
			String mentorSkills_old  = c.mentorSkills;
			
			updateCourseFunc(name_old,description_old,requiredSkills_old,acquiredSkills_old,mentorSkills_old,c);
			
			System.out.println("Course has been updated successfully");
			courses = entitymanager.createQuery("from Course", Course.class).getResultList();
			
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
	
	private void updateCourseFunc(String name,String description,String requiredSkills,String acquiredSkills,String mentorSkills,Course co){
		
		int  count  =0;
		System.out.println("Old Course name is " +name+ ".leave blank if you do not want to change the value ");
		System.out.println("Enter the new Course name: ");
		String new_name =  scanner.nextLine();
		if (new_name.equals(" ")) {
			co.setName(name);
			count++;
		}else {
			co.setName(new_name);
			count++;
		}
		
		System.out.println("Old Description is " +description+ ".leave blank if you do not want to change the value ");
		System.out.println("Enter the new Description: ");
		String new_description=  scanner.nextLine();
		if (new_description.equals(" ")) {
			co.setDescription(description);
		}else {
			co.setDescription(new_description);
		}
		
		System.out.println("Old Required Skill is " +requiredSkills+ ".leave blank if you do not want to change the value ");
		System.out.println("Enter the new Required Skills: ");
		String new_requiredSkills =  scanner.nextLine();
		if (new_requiredSkills.equals(" ")) {
			co.setRequiredSkills(requiredSkills);
		}else {
			co.setRequiredSkills(new_requiredSkills);
		}
		
		System.out.println("Old Acquired Skill is " +acquiredSkills+ " .leave blank if you dont want to change the value ");
		System.out.println("Enter the new Acquired Skills: ");
		String new_acquiredSkills =  scanner.nextLine();
		if (new_acquiredSkills.equals(" ")) {
			co.setAcquiredSkills(acquiredSkills);
		}else {
			co.setAcquiredSkills(new_acquiredSkills);
		}
		
		System.out.println("Old Mentor Skill is " +mentorSkills+ ".leave blank if you do not want to change the value ");
		System.out.println("Enter the new Mentor Skills: ");
		String new_mentorSkills =  scanner.nextLine();
		if (new_mentorSkills.equals(" ")) {
			co.setMentorSkills(mentorSkills);
		}else {
			co.setMentorSkills(new_mentorSkills);
		}
		
		entitymanager.merge(co);
		entitymanager.getTransaction().commit();

		
		
	}
	
	
}
		

