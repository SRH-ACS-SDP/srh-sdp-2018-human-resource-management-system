package sdpsose2018.hrms;
import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


public class TrainingManager {

	EntityManager entitymanager;
	List<Training> trainigs;
	Scanner scanner;
	int courseId;
	int conductorId;
	char c;
	int id;
	static BufferedReader br ;
	
	public TrainingManager (EntityManager em, Scanner sc) {
		this.entitymanager = em;
		this.scanner = sc;
		trainigs = em.createQuery("from Training", Training.class).getResultList();
	}
	
	public void addTraining() {
		
		Training tr = new Training();
		CourseManager mc = new CourseManager(entitymanager, scanner);

		boolean is_valid  =  true;
		
		System.out.println("_____Adding a new Training_____\n");
		
		do {
		System.out.print("Course ID: ");
		System.out.print("Do you want to view All Courses?(y/n) ");
		c = scanner .nextLine().charAt(0);
		
		if (validateViewCourseInput(c)) {
			is_valid  =  false;
			if (c == 'y') {
			mc.viewAllCourse();
			do {
			System.out.println("Course is exist? (y/n)");
			c = scanner .nextLine().charAt(0);
			
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
			c = scanner .nextLine().charAt(0);
			is_valid  =  true;
			
			if (validateViewCourseInput(c)) {
				is_valid  =  false;
					
					if (c == 'y') {
						System.out.print("Enter your ID: ");
						int conductorId = Integer.parseInt(scanner .nextLine());
						tr.setConductorId(conductorId);
						
					}else if (c == 'n') {
						System.out.print("Do you want to view employees?(y/n) ");
						c = scanner .nextLine().charAt(0);
						
						if (c == 'y') {
							
							//Call: ViewEmployee();
							conductorId = validateCourseIsExistInput();
							if (conductorId == 0) {
								return;
							}
							tr.setConductorId(conductorId);
							
						}else if (c == 'n') {
							conductorId = validateCourseIsExistInput();
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
				
				entitymanager.getTransaction().begin();
				entitymanager.persist(tr);
				entitymanager.getTransaction().commit();
		
		System.out.print("Added a Record Successfully!!!!!!!");
		
	}
	
	public void viewTraining() {
		
		StringBuilder stringBuilder = new StringBuilder();
		System.out.println("\n_____View all available Trainings_____");
		stringBuilder.append(String.format("%147s\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-16s|%-25s|%-25s|%-25s|%-25s|%-25s|\n", "TRAINING ID","EMPLOYEE ID", "COURSE ID", "CONDUCTOR ID", "DATE", "RESULT").replace(' ', '_'));
		
		for (Training t : trainigs) {
			
			stringBuilder.append(String.format("|%16d|", t.getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getEmployeeId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getCourseId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getConductorId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getDate()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", t.getResult()).replace(' ', '_'));

			stringBuilder.append("\n");
		}
		
		System.out.println(stringBuilder.toString());
	}
	
	public void updateTraining() {
		
		entitymanager.getTransaction().begin();
		
		boolean is_valid  =  true;
		try {
			System.out.println("_____Updating a Training_____\n");
			do {
				System.out.print("Do you want to view Trainings?(y/n) ");
				c = scanner .nextLine().charAt(0);
				if (validateViewCourseInput(c)) {
					is_valid  =  false;
					
					if (c == 'y') {
						viewTraining();
						do{
							System.out.print("Enter Training ID: ");
							id  = scanner.nextInt();
							Query query =entitymanager.createQuery("Select id from Training c where c.id LIKE :id");
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
							id  = scanner.nextInt();
							Query query =entitymanager.createQuery("Select id from Training c where c.id LIKE :id");
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
		
			Query query =entitymanager.createQuery("Select id from Training c where c.id LIKE :id");
			query.setParameter("id", id);
			List ids = query.getResultList();
			Training t  = entitymanager.find(Training.class,ids.get(0));
			
			String employeeId_old  = t.employeeId;
			int courseId_old  = t.courseId;
			int conductorId_old   =  t.conductorId;
			int date_old  = t.date;
			String result_old  = t.result;
			
			updateTrainingFunc(employeeId_old,courseId_old,conductorId_old,date_old,result_old,t);
			
			System.out.println("\nTraining has been updated successfully!!!!");
			trainigs = entitymanager.createQuery("from Training", Training.class).getResultList();
			
			}catch(Exception e) {
				e.printStackTrace();
			}	
	}
		
		
	private void updateTrainingFunc(String employeeId_old,int courseId_old,int conductorId_old,int date_old,String result_old,Training tr) {
		
		CourseManager mc = new CourseManager(entitymanager, scanner);

		int  count  =0;
		System.out.println("\nEmployee ID is " +employeeId_old+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter new Employee ID: ");
		scanner.nextLine();
		String employeeId_new =  scanner.nextLine();
		if (employeeId_new.equals("")) {
			tr.setEmployeeId(employeeId_old);
			count++;
		}else {
			tr.setEmployeeId(employeeId_new);
			count++;
		}
		
		boolean is_valid  =  true;
		
		System.out.println("\nCourse ID is " +courseId_old);
		do {
		System.out.print("Do you want to Edit the Course " +courseId_old+ " ?(y/n)");
		c = scanner .nextLine().charAt(0);
		
		if (validateViewCourseInput(c)) {
			is_valid  =  false;
			
			if (c == 'y') {
				mc.updateSpecificCourse(courseId_old);
			}else if (c == 'n') {
				System.out.print("Do you want to view All Courses?(y/n)");
				c = scanner .nextLine().charAt(0);
		
				if (c == 'y') {
					mc.viewAllCourse();
					System.out.println("(leave blank if you do not want to change the value)");
					System.out.print("Enter new Course ID: ");
					String CourseId_new = scanner.nextLine();
						if (CourseId_new.equals("")) {
							tr.setCourseId(courseId_old);
						}else {
							tr.setCourseId(Integer.parseInt(CourseId_new));
						}
			
				}else if(c == 'n') {
					System.out.println("(leave blank if you do not want to change the value)");
					System.out.print("Enter new Course ID: ");
					String CourseId_new = scanner.nextLine();
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
		
		System.out.println("\nConductor ID is " +conductorId_old+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter new Conductor ID:  ");
		String conductorId_new =  scanner.nextLine();
		if (conductorId_new.equals("")) {
			tr.setConductorId(conductorId_old);
		}else {
			tr.setConductorId(Integer.parseInt(conductorId_new));
		}
		
		
		System.out.println("\nDate is " +date_old+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter new Date:  ");
		String date_new =  scanner.nextLine();
		if (date_new.equals("")) {
			tr.setDate(date_old);
		}else {
			tr.setDate(Integer.parseInt(date_new));
		}
		
		System.out.println("\nResult is " +result_old+ " (leave blank if you do not want to change the value)");
		System.out.print("Enter new Result:  ");
		String result_new =  scanner.nextLine();
		if (result_new.equals("")) {
			tr.setResult(result_old);
		}else {
			tr.setResult(result_new);
		}
		
		entitymanager.merge(tr);
		entitymanager.getTransaction().commit();
		
		}
		
	////Validation
	public int validateCourseIsExistInput() {
		
		boolean isInputValid = false;
		do {
			System.out.print("Enter Course ID: ");
			int intCourseId = 0;
			try {
				intCourseId = Integer.parseInt(scanner.nextLine());
				entitymanager.createQuery("from Course where id = '"+ courseId +"'").getSingleResult();	
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
					switch (scanner.nextLine().charAt(0)) {
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
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				isInputValid = false;
			}
		} while (!isInputValid);
		
		return 0;
		
	}

	
	public int validateConductorIdInput() {
		
		boolean isInputValid = false;
		do {
			System.out.print("Enter Conductor ID: ");
			int intConductorId = 0;
			try {
				intConductorId = Integer.parseInt(scanner.nextLine());
				entitymanager.createQuery("from Course where id = '"+ conductorId +"'").getSingleResult();	
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
					switch (scanner.nextLine().charAt(0)) {
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
			}
			catch (Exception e) {
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
	
	
}

