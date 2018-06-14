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
	char c;
	String name;
	int id;
	
	

	public CourseManager(EntityManager em, Scanner sc) {
		this.entitymanager = em;
		this.scanner = sc;
		
		
		courses = em.createQuery("from Course", Course.class).getResultList();
	}
	
	//Add
	
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
	    		System.out.print("Try again? (y/n): ");
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
		System.out.println("Course has been added successfully!!!!");
		entitymanager.getTransaction().commit();
		
		courses = entitymanager.createQuery("from Course", Course.class).getResultList();
}
	
	//View
	
	public void viewAllCourse() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("\n_____View all available Courses_____");
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
	
	public void viewSpecificCourse(int courseId) {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("\n_____View Course ID: " +courseId+ "_____");
		stringBuilder.append(String.format("%142s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "COURSE ID","NAME", "DESCRIPTION", "REQUIRED SKILLS", "ACQUIRED SKILLS", "MENTOR SKILLS").replace(' ', '_'));
		courses = entitymanager.createQuery("from Course where id = '"+courseId+"'", Course.class).getResultList();
		
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

	//Update
	
	public void updateSpecificCourse(int courseId) {
		
		boolean is_valid  =  true;
		try {
			System.out.println("\n_____Updating_a_Course_:"+courseId+"_____\n");
			do {
			System.out.print("Do you want to view the Course " +courseId+ " ?(y/n): ");
			c = scanner .nextLine().charAt(0);
			
			if (TrainingManager.validateViewCourseInput(c)) {
				is_valid  =  false;
									
				if (c == 'y') {
						viewSpecificCourse(courseId);
						Query query =entitymanager.createQuery("Select id from Course c where c.id LIKE :id");
						query.setParameter("id", id);
						
						
				}else if (c == 'n') {
						Query query =entitymanager.createQuery("Select id from Course c where c.id LIKE :id");
						query.setParameter("id", id);
							
				}
			}else {
				System.out.println("Invalid Input!!!");
				System.out.println();
			}
			}while(is_valid);
			
			Course c  = entitymanager.find(Course.class,courseId);
			
			String name_old  = c.name;
			String description_old  = c.description;
			String requiredSkills_old   =  c.requiredSkills;
			String acquiredSkills_old  = c.acquiredSkills;
			String mentorSkills_old  = c.mentorSkills;
			
			updateCourseFunc(name_old,description_old,requiredSkills_old,acquiredSkills_old,mentorSkills_old,c);
			
			System.out.println("\nCourse has been updated successfully!!!!");
			System.out.print("______________________________________________________________");
			courses = entitymanager.createQuery("from Course", Course.class).getResultList();
			
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
	
	private void updateCourseFunc(String name,String description,String requiredSkills,String acquiredSkills,String mentorSkills,Course co){
		
		int  count  =0;
		System.out.println("\nCourse name is " +name+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter the new Course name: ");
		String new_name =  scanner.nextLine();
		if (new_name.equals("")) {
			co.setName(name);
			count++;
		}else {
			co.setName(new_name);
			count++;
		}
		
		System.out.println("\nDescription is " +description+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter the new Description: ");
		String new_description=  scanner.nextLine();
		if (new_description.equals("")) {
			co.setDescription(description);
		}else {
			co.setDescription(new_description);
		}
		
		System.out.println("\nRequired Skill is " +requiredSkills+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter the new Required Skills: ");
		String new_requiredSkills =  scanner.nextLine();
		if (new_requiredSkills.equals("")) {
			co.setRequiredSkills(requiredSkills);
		}else {
			co.setRequiredSkills(new_requiredSkills);
		}
		
		System.out.println("\nAcquired Skill is " +acquiredSkills+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter the new Acquired Skills: ");
		String new_acquiredSkills =  scanner.nextLine();
		if (new_acquiredSkills.equals("")) {
			co.setAcquiredSkills(acquiredSkills);
		}else {
			co.setAcquiredSkills(new_acquiredSkills);
		}
		
		System.out.println("\nMentor Skill is " +mentorSkills+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter the new Mentor Skills: ");
		String new_mentorSkills =  scanner.nextLine();
		if (new_mentorSkills.equals("")) {
			co.setMentorSkills(mentorSkills);
		}else {
			co.setMentorSkills(new_mentorSkills);
		}
		
	}
	

	
}
		

