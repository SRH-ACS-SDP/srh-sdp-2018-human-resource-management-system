package sdpsose2018.hrms;
import java.util.List;
import java.util.Scanner;
import javax.persistence.Query;
import javax.persistence.NoResultException;

public class CourseManager {

	DatabaseConnection em;
	List<Course> courses;
	List<Training> trainigs;
	Scanner sc;
	char c;
	String name;
	int id;
	
	

	public CourseManager(DatabaseConnection em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		courses = em.createQuery("from Course", Course.class).getResultList();
	}
	
	public void fetchCourse() {
		trainigs = em.createQuery("from Training", Training.class).getResultList();
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
			coursename = sc.nextLine();
			co.setName(coursename);
	
		try {
			em.createQuery("from Course where name = '"+ coursename +"'").getSingleResult();
			System.out.println("Course already exists in Database.");
    		boolean isRetry = true;
			do {
	    		System.out.print("Try again? (y/n): ");
	    		switch(sc.nextLine().charAt(0)) {
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
	
		em.persist(co);
		fetchCourse();
		System.out.println("Course has been added successfully!!!!");
}
	
	//View
	public void viewAllCourse() {
		fetchCourse();
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
		fetchCourse();
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("\n_____View Course ID: " +courseId+ "_____");
		stringBuilder.append(String.format("%142s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "COURSE ID","NAME", "DESCRIPTION", "REQUIRED SKILLS", "ACQUIRED SKILLS", "MENTOR SKILLS").replace(' ', '_'));
		courses = em.createQuery("from Course where id = '"+courseId+"'", Course.class).getResultList();
		
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
			c = sc .nextLine().charAt(0);
			if (TrainingManager.validateViewCourseInput(c)) {
				is_valid  =  false;
									
				if (c == 'y') {
						viewSpecificCourse(courseId);
						Query query =em.createQuery("Select id from Course c where c.id = :updateID");
						query.setParameter("updateID", courseId);
						
						
				}else if (c == 'n') {
						Query query =em.createQuery("Select id from Course c where c.id = :updateID");
						query.setParameter("updateID", courseId);
				}
			}else {
				System.out.println("Invalid Input!!!");
				System.out.println();
			}
			}while(is_valid);
			
			Course c  = em.find(Course.class,courseId);
			
			String name_old  = c.name;
			String description_old  = c.description;
			String requiredSkills_old   =  c.requiredSkills;
			String acquiredSkills_old  = c.acquiredSkills;
			String mentorSkills_old  = c.mentorSkills;
			
			updateCourseFunc(name_old,description_old,requiredSkills_old,acquiredSkills_old,mentorSkills_old,c);
			System.out.println("\nCourse has been updated successfully!!!!");
			System.out.println("______________________________________________________________");
			
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
	
	private void updateCourseFunc(String name,String description,String requiredSkills,String acquiredSkills,String mentorSkills,Course co){
		
		System.out.println("\nCourse name is " +name+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter the new Course name: ");
		String new_name =  sc.nextLine();
		if (!new_name.equals("")) {
			co.setName(new_name);
		}
		
		System.out.println("\nDescription is " +description+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter the new Description: ");
		String new_description=  sc.nextLine();
		if (!new_description.equals("")) {
			co.setDescription(new_description);
		}
		
		System.out.println("\nRequired Skill is " +requiredSkills+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter the new Required Skills: ");
		String new_requiredSkills =  sc.nextLine();
		if (!new_requiredSkills.equals("")) {
			co.setRequiredSkills(new_requiredSkills);
		}
		
		System.out.println("\nAcquired Skill is " +acquiredSkills+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter the new Acquired Skills: ");
		String new_acquiredSkills =  sc.nextLine();
		if (!new_acquiredSkills.equals("")) {
			co.setAcquiredSkills(new_acquiredSkills);
		}
		
		System.out.println("\nMentor Skill is " +mentorSkills+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter the new Mentor Skills: ");
		String new_mentorSkills =  sc.nextLine();
		if (!new_mentorSkills.equals("")) {
			co.setMentorSkills(new_mentorSkills);
		}
}
	
	public void deleteCourse() {
		
		boolean is_valid = true;
			System.out.println("\n_____Deleting_a_Course_:_____\n");
			do {
				System.out.print("Do you want to view Courses ?(y/n): ");
				c = sc.nextLine().charAt(0);
					if (TrainingManager.validateViewCourseInput(c)) {
						is_valid  =  false;
			
			if (c == 'y') {
				viewAllCourse();
			do {
				System.out.print("\nEnter Course ID: ");
				try {
					id = Integer.parseInt(sc.nextLine());
					Query query =em.createQuery("Select id from Course c where c.id = :id");
					query.setParameter("id", id);
					List ids = query.getResultList();
						if(ids.size() != 0 ){
							is_valid = false;
							}else{
								System.out.println("Please enter a valid Training ID");
								is_valid = true;
							}
					}catch (NumberFormatException nfe) {
							System.out.println("Course ID not exists in Database");
							is_valid = true;
					}
			}while(is_valid);
				
			}else if (c == 'n') {
				
			do {
				System.out.print("\nEnter Course ID: ");
				try {				
					id = Integer.parseInt(sc.nextLine());
					Query query =em.createQuery("Select id from Course c where c.id = :id");
					query.setParameter("id", id);
					List ids = query.getResultList();
						if(ids.size() != 0 ){
							is_valid = false;
							}else{
								System.out.println("Course ID not exists in Database");
								is_valid = true;
							}
					}catch (NumberFormatException nfe) {
							System.out.println("Invalid Input");
							is_valid = true;
					}
			}while(is_valid);
			}
				Course c = em.find(Course.class, id );
				em.remove(c);
				fetchCourse();
				System.out.println("\nCourse has been deleted successfully!!!!");
				System.out.print("______________________________________________________________");
		}else {
			System.out.println("Invalid Input!");
			System.out.println();
		}
		}while(is_valid);
	}
		
}
