package sdpsose2018.hrms;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class TrainingManager {

	EntityManager em;
	List<Training> trainigs;
	Scanner sc;
	int courseId;
	int conductorId;
	char c;
	int id;
	List<Integer> ids;
	
	
	public TrainingManager (EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		trainigs = em.createQuery("from Training", Training.class).getResultList();
	}
	
	public void addTraining() {
		CourseManager mc = new CourseManager(em, sc);
		Training tr = new Training();
		
		boolean is_valid  =  true;
		
		System.out.println("_____Adding a new Training_____\n");
		
		do {
		System.out.print("Course ID: ");
		System.out.print("Do you want to view All Courses?(y/n): ");
		c = sc .nextLine().charAt(0);
		
		if (validateViewCourseInput(c)) {
			is_valid  =  false;
			if (c == 'y') {
				mc.viewAllCourse();
			do {
				System.out.print("Course is exist? (y/n): ");
				c = sc .nextLine().charAt(0);
			
				is_valid  =  true;
			if (validateViewCourseInput(c)) {
				is_valid  =  false;
			
				if (c == 'y') {
					courseId = validateCourseIsExistInput();
					if (courseId == 0) {
						return;
					}
					tr.setCourseId(courseId);
				
				}else if(c == 'n'){
					mc.addCourse();
					mc.viewAllCourse();
					courseId = validateCourseIsExistInput();
					if (courseId == 0) {
						return;
					}
					tr.setCourseId(courseId);
				}
			}else {
				System.out.println("Invalid Input!");
				}
			}while(is_valid);
	
			}else if (c == 'n') {
				courseId = validateCourseIsExistInput();
				if (courseId == 0) {
					return;
				}
				tr.setCourseId(courseId);		
			}
		}else {
			System.out.println("Invalid Input!");
			System.out.println();
		}
		}while(is_valid);
		
		//Add Conductor ID
		
		do {
			System.out.print("Conductor ID: ");
			System.out.print("Are you going to conduct the Training(y/n)? ");
			c = sc .nextLine().charAt(0);
			is_valid  =  true;
			if (validateViewCourseInput(c)) {
				is_valid  =  false;
					
					if (c == 'y') {
						System.out.print("Enter your ID: ");
						int conductorId = Integer.parseInt(sc .nextLine());
						tr.setConductorId(conductorId);
						
					}else if (c == 'n') {
						System.out.print("Do you want to view employees?(y/n) ");
						c = sc .nextLine().charAt(0);
						
						if (c == 'y') {
							
							//Call: ViewEmployee();
							conductorId = validateConductorIdInput();
							if (conductorId == 0) {
								return;
							}
							tr.setConductorId(conductorId);
							
						}else if (c == 'n') {
							conductorId = validateConductorIdInput();
							if (conductorId == 0) {
								return;
							}
							tr.setConductorId(conductorId);
						}
					}	
			}
			else {
				System.out.println("Invalid Input!");
				System.out.println();
			}
		}while(is_valid);
				
				em.getTransaction().begin();
				em.persist(tr);
				em.getTransaction().commit();
		System.out.println("\nAdded a Record Successfully!!!!!!!");
		System.out.print("______________________________________________________________");
		whatNext();
		
	}
	
	public void viewTraining() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("\n_____View all available Trainings_____");
		stringBuilder.append(String.format("%147s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-16s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "TRAINING ID","EMPLOYEE ID", "COURSE ID", "CONDUCTOR ID", "DATE", "RESULT").replace(' ', '_'));
		
		for (Training t : trainigs) {
			
			stringBuilder.append(String.format("|%16d|", t.getId()).replace(' ', '_'));
			if(t.getEmployeeId() != null) {
				stringBuilder.append(String.format("%-25s|", t.getEmployeeId()).replace(' ', '_'));
			} else {
				stringBuilder.append(String.format("%-25s|", "".replace(' ', '_')));
			}
				
			stringBuilder.append(String.format("%-25s|", t.getCourseId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getConductorId()).replace(' ', '_'));
			if(t.getEmployeeId() != null) {
				stringBuilder.append(String.format("%-25s|", new SimpleDateFormat("yyyy.MM.dd").format(t.getDate())).replace(' ', '_'));
			} else {
				stringBuilder.append(String.format("%-25s|","".replace(' ', '_')));
			}
			
			if(t.getEmployeeId() != null) {
				stringBuilder.append(String.format("%-25s|", t.getResult()).replace(' ', '_'));
			} else {
				stringBuilder.append(String.format("%-25s|","".replace(' ', '_')));
			}
			

			stringBuilder.append("\n");
		}
		System.out.println(stringBuilder.toString());
	}
	
	public void updateTraining() {
		
		em.getTransaction().begin();
		
		boolean is_valid  =  true;
		try {
			System.out.println("_____Updating a Training_____\n");
			do {
				System.out.print("Do you want to view Trainings?(y/n) ");
				c = sc .nextLine().charAt(0);
				if (validateViewCourseInput(c)) {
					is_valid  =  false;
					
					if (c == 'y') {
						viewTraining();
						do{
							System.out.print("Enter Training ID: ");
							id  = Integer.parseInt(sc.nextLine());
							Query query =em.createQuery("Select id from Training c where c.id = :id");
							query.setParameter("id", id);
							List ids = query.getResultList();
							if(ids.size() != 0 ){
								is_valid = false;
							}else{
								System.out.println("Please enter a valid Training ID");
								is_valid = true;
							}
						}while(is_valid);
			
					}else if (c == 'n') {
						do{
							System.out.print("\nEnter Training ID: ");
							id  = Integer.parseInt(sc.nextLine());
							Query query =em.createQuery("Select id from Training c where c.id = :id");
							query.setParameter("id", id);
							List ids = query.getResultList();
							if(ids.size() != 0 ){
								is_valid = false;
							}else{
								System.out.println("Please enter a valid Training ID");
								is_valid = true;
							}
						}while(is_valid);
					}
				}else {
					System.out.println("Invalid Input!");
					System.out.println();
				}
			}while(is_valid);
		
			Query query =em.createQuery("Select id from Training c where c.id = :id");
			query.setParameter("id", id);
			List ids = query.getResultList();
			Training t  = em.find(Training.class,ids.get(0));
			
			String employeeId_old  = t.employeeId;
			int courseId_old  = t.courseId;
			int conductorId_old   =  t.conductorId;
			Date date_old  = t.date;
			String result_old  = t.result;
			
			updateTrainingFunc(employeeId_old,courseId_old,conductorId_old,date_old,result_old,t);
			System.out.println("\nTraining has been updated successfully!!!!");
			System.out.println("______________________________________________________________");
			trainigs = em.createQuery("from Training", Training.class).getResultList();
			whatNext();
			
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
		
		
	private void updateTrainingFunc(String employeeId_old,int courseId_old,int conductorId_old,Date date_old,String result_old,Training tr) {
		
		CourseManager mc = new CourseManager(em, sc);

		System.out.println("\nEmployee ID is " +employeeId_old+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter new Employee ID: ");
		String employeeId_new =  sc.nextLine();
		if (employeeId_new.equals("")) {
			tr.setEmployeeId(employeeId_old);
		}else {
			tr.setEmployeeId(employeeId_new);
		}
		
		boolean is_valid  =  true;
		System.out.println("\nCourse ID is " +courseId_old);
		do {
		System.out.print("Do you want to Edit the Course " +courseId_old+ " ?(y/n)");
		c = sc .nextLine().charAt(0);
		
		if (validateViewCourseInput(c)) {
			is_valid  =  false;
			
			if (c == 'y') {
				mc.updateSpecificCourse(courseId_old);
			}else if (c == 'n') {
				System.out.print("Do you want to view All Courses?(y/n)");
				c = sc .nextLine().charAt(0);
		
				if (c == 'y') {
					mc.viewAllCourse();
					System.out.println("(Nothing to update -> Please leave blank.)");
					System.out.print("Enter new Course ID: ");
					String CourseId_new = sc.nextLine();
						if (CourseId_new.equals("")) {
							tr.setCourseId(courseId_old);
						}else {
							tr.setCourseId(Integer.parseInt(CourseId_new));
						}
			
				}else if(c == 'n') {
					System.out.println("\n(Nothing to update -> Please leave blank.)");
					System.out.print("Enter new Course ID: ");
					String CourseId_new = sc.nextLine();
						if (CourseId_new.equals("")) {
							tr.setCourseId(courseId_old);
						}else {
							tr.setCourseId(Integer.parseInt(CourseId_new));
						}
				}
			}
		}else {
			System.out.println("Invalid Input!!!");
			System.out.println();
		}
		}while(is_valid);
		
		System.out.println("\nConductor ID is " +conductorId_old+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter new Conductor ID:  ");
		String conductorId_new =  sc.nextLine();
		if (conductorId_new.equals("")) {
			tr.setConductorId(conductorId_old);
		}else {
			tr.setConductorId(Integer.parseInt(conductorId_new));
		}
		
		boolean isDateValid = false;
		do {
			System.out.println("\nDate is " + date_old + " (Nothing to update -> Please leave blank.)");
			System.out.print("Enter new Date (YYYY.MM.DD):  ");
			String date_new_string =  sc.nextLine();
			
			if(!date_new_string.equals("")) {
				Pattern pattern = Pattern.compile("[1-9][0-9]*.(1[0-2]|0[1-9]).(0[1-9]|(1|2)[0-9]|3[0-1])");
				Matcher matcher = pattern.matcher(date_new_string);
			
				isDateValid = matcher.matches();
			
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date date;
				try {
					date = dateFormat.parse(date_new_string);
					tr.setDate(date);
					isDateValid = true;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isDateValid = false;
				}
			}
		} while (!isDateValid);
		
		System.out.println("\nResult is " +result_old+ " (Nothing to update -> Please leave blank.)");
		System.out.print("Enter new Result:  ");
		String result_new =  sc.nextLine();
		if (result_new.equals("")) {
			tr.setResult(result_old);
		}else {
			tr.setResult(result_new);
		}		
		em.merge(tr);
		em.getTransaction().commit();
}
	
	public void deleteTraining() {
		
		boolean is_valid = true;
			System.out.println("_____Deleting a Training_____\n");
		do {
			System.out.print("Do you want to view Trainings?(y/n): ");
				c = sc .nextLine().charAt(0);
				if (validateViewCourseInput(c)) {
					is_valid  =  false;
			
			if (c == 'y') {
					viewTraining();
				do {
						System.out.print("\nEnter Training ID :");
					try {
						id  = Integer.parseInt(sc.nextLine());
						Query query =em.createQuery("Select id from Training c where c.id = :id");
						query.setParameter("id", id);
						List ids = query.getResultList();
							if(ids.size() != 0 ){
								is_valid = false;
								}else{
									System.out.println("Please enter a valid Training ID");
									is_valid = true;
								}
						}catch (NumberFormatException nfe) {
								System.out.println("Please enter a valid Training ID");
								is_valid = true;
						}
					}while(is_valid);
				
			}else if (c == 'n') {
				do {
						System.out.print("\nEnter Training ID: ");
					try {
						id  = Integer.parseInt(sc.nextLine());
						Query query =em.createQuery("Select id from Training c where c.id = :id");
						query.setParameter("id", id);
						List ids = query.getResultList();
							if(ids.size() != 0 ){
								is_valid = false;
							}else{
								System.out.println("Please enter a valid Training ID");
								is_valid = true;
							}
					}catch (NumberFormatException nfe) {
				        	System.out.println("Please enter a valid Training ID");
				        	is_valid = true;
					}
				}while(is_valid);
			}
				Training t  = em.find(Training.class,id);
				em.getTransaction().begin();
				em.remove(t);
				em.getTransaction().commit();
				System.out.println("\nTraining has been deleted!!!!");
				System.out.println("______________________________________________________________");
			}else {
					System.out.println("Invalid Input!");
					System.out.println();
			}
		}while(is_valid);
	}

		

		
	////Validation
	public int validateCourseIsExistInput() {
		
		boolean isInputValid = false;
		do {
			System.out.print("Enter Course ID: ");
			int intCourseId = 0;
			try {
				intCourseId = Integer.parseInt(sc.nextLine());
				em.createQuery("from Course where id = '"+ courseId +"'").getSingleResult();	
				System.out.println("Course already exists in Database.");
			} catch (NoResultException nre) {
				isInputValid = true;
				return intCourseId;
			} catch (NumberFormatException nfe) {
        		System.out.println("Not a valid number.");
				isInputValid = false;
				boolean isRetry = false;
				do {
					System.out.print("Try again? (y/n) ");
					switch (sc.nextLine().charAt(0)) {
					case 'y':
						isRetry = true;
						continue;
					case 'n':
						System.out.print("You exist from the Adding Trainigs!!!");
						return 0;
					default:
						isRetry = false;
						break;
					}					
				}while (!isRetry);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				isInputValid = false;
			}
		}while (!isInputValid);
		return 0;		
	}

	public int validateConductorIdInput() {
		
		boolean isInputValid = false;
		do {
			System.out.print("Enter Conductor ID: ");
			int intConductorId = 0;
			try {
				intConductorId = Integer.parseInt(sc.nextLine());
				em.createQuery("from Course where id = '"+ conductorId +"'").getSingleResult();	
				System.out.println("Course already exists in Database.");
			} catch (NoResultException nre) {
				isInputValid = true;
				return intConductorId;
			} catch (NumberFormatException nfe) {
        		System.out.println("Not a valid number.");
				isInputValid = false;
				boolean isRetry = false;
				do {
					System.out.print("Try again? (y/n) ");
					switch (sc.nextLine().charAt(0)) {
					case 'y':
						isRetry = true;
						continue;
					case 'n':
						System.out.print("You exist from the Adding Trainigs!!!");
						return 0;
					default:
						isRetry = false;
						break;
					}					
				} while (!isRetry);
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
				isInputValid = false;
			}
		} while (!isInputValid);
		
		return 0;
	}
	
	public static boolean validateViewCourseInput(char ch) {
		
	boolean  flag  = false;
		if(ch == 'y' || ch == 'n') {
			flag  =  true;
			return flag;
		}else {
			return flag;
		}
	}
	
	public void menuForTrainingManager()
	{
		CourseManager mc = new CourseManager(em, sc);
		boolean is_valid  =  true;
		int number = 0;
	
		System.out.println("\n_____Training_Module_____\n");
	do {
		System.out.println("1. New Training ");
		System.out.println("2. Edit Training ");
		System.out.println("3. View Training ");
		System.out.println("4. Delete Training ");
		try {
			System.out.print("Input ->");
			number  =  Integer.parseInt(sc.nextLine());
		
			switch(number) {
		
				case 1 :addTraining();
						is_valid = false;
						break;
			
				case 2 :updateTraining();
						is_valid = false;
						break;
						
				case 3 :viewTraining();
						is_valid = false;
						break;
		
				case 4 : System.out.print("\nDo you need to Delete a Training?(y/n): ");
						char c = sc.nextLine().charAt(0);
							if (c == 'y') {
								deleteTraining();
								whatNext();
							}else {
								mc.deleteCourse();
								whatNext();
							}
						is_valid = false;
						break;
		
				default: System.out.println("Invalid Number. Try again ! ");
						is_valid = true;
			}	
		}catch (Exception e) {
			System.out.println("Invalid Number. Try again ! ");
			is_valid = true;
		}
		}while(is_valid);
	}

	public void whatNext(){
		
		boolean is_valid  =  true;
		do {
			try {
				System.out.print("\nDo you want to goto the Training Manager Menu ? (y/n):");
				char c = sc.nextLine().charAt(0);
		
					if(c == 'y') {
						menuForTrainingManager();
						is_valid = false;
					}
					else if(c == 'n') {
						System.out.println("Thank you");
						is_valid = false;
					}else {
						System.out.println("Invalid Entry. Try again ! ");
						is_valid = true;
					}
			}catch(Exception e){
				System.out.println("Invalid Entry. Try again ! ");
				is_valid = true;
			}
	}while(is_valid);
	}
		
	
}
	

