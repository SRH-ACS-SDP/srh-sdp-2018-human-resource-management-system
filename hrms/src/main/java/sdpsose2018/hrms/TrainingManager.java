package sdpsose2018.hrms;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class TrainingManager {

	EntityManager entitymanager;
	List<Training> trainig;
	Scanner scanner;
	int courseId;
	int conductorId;
	char c;
	
	
	
	public TrainingManager (EntityManager em, Scanner sc) {
		this.entitymanager = em;
		this.scanner = sc;
		

		trainig = em.createQuery("from Training", Training.class).getResultList();
	}
	
	public void addTraining() {
		
		Training tr = new Training();
		CourseManager mc = new CourseManager(entitymanager, scanner);
		
		
		//Add Course ID
		
		System.out.println("_____Adding a new Training_____\n");
		System.out.print("Course ID: ");
		System.out.print("Do you want to view Courses?(y/n) ");
		c = scanner .nextLine().charAt(0);
		System.out.println();
		
		if (c == 'y') {
			
			mc.viewCourse();
			System.out.print("Course is exist? (y/n)");
			c = scanner .nextLine().charAt(0);
			System.out.println();
			
				if (c == 'y') {
					courseId = validateCourseIdInput();//validate inputs contain with numbers only
					if (courseId == 0) {
						return;
					}
					tr.setCourseId(courseId);
				
				}else if(c == 'n'){
					mc.addCourse();
					mc.viewCourse();
					courseId = validateCourseIdInput();
					if (courseId == 0) {
						return;
					}
					tr.setCourseId(courseId);
				}
	
		}else if (c == 'n') {
			courseId = validateCourseIdInput();
			if (courseId == 0) {
				return;
			}
			tr.setCourseId(courseId);
			
		}else {
			/////////////need to validate random values without yes or no
			
		}

		//Add Conductor ID
				System.out.print("Conductor ID: ");
				System.out.print("Are you going to conduct the Training(y/n) ");
				c = scanner .nextLine().charAt(0);
				
				if (c == 'y') {
					
					System.out.print("Enter your ID: ");
					int conductorId = Integer.parseInt(scanner .nextLine());
					tr.setConductorId(conductorId);
					
				}else if (c == 'n') {
					System.out.print("Do you want to view employees?(y/n) ");
					c = scanner .nextLine().charAt(0);
					
					if (c == 'y') {
						
						//Call: ViewEmployee();
						conductorId = validateConductorIdInput();//validate inputs contain with numbers only
						if (conductorId == 0) {
							return;
						}
						tr.setConductorId(conductorId);
						
					}else if (c == 'n') {
						conductorId = validateConductorIdInput();//validate inputs contain with numbers only
						if (conductorId == 0) {
							return;
						}
						tr.setConductorId(conductorId);
					
				}else {
					System.out.print("Enter valid input");
					addTraining();
				}
					
				}
				entitymanager.getTransaction().begin();
				entitymanager.persist(tr);
				entitymanager.getTransaction().commit();
		
		System.out.print("Added a Record Successfully!!!!!!!");
			
	}
	
	public int validateCourseIdInput() {
		
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
}

