package sdpsose2018.hrms;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class EmployeeManager {

	List<Employee> employees;
	EntityManager em;
	Scanner scanner;
	
	public EmployeeManager(EntityManager em, Scanner scanner) {
		
		this.em = em;
		this.scanner = scanner;
		
		fetchEmployees();		
	}
	
	public void start() {
		do {
			System.out.println("- Manage your Employees -");
			System.out.println("Please enter the number of one option below to continue.");
			System.out.println("1.) View employees.");
			System.out.println("2.) Add an employee.");
			System.out.println("3.) Update an employee.");
			System.out.println("4.) Delete an employee.");
			System.out.println("5.) Manage an employee's payroll.");
			System.out.println("6.) Go back to main menu.");
			
			try {
				int input = Integer.parseInt(scanner.nextLine());
				switch(input) {
				case 1:
					viewEmployees();
					break;
				case 2:
					addEmployee();
					break;
				case 3:
					updateEmployee();
					break;
				case 4:
					deleteEmployee();
					break;
				case 5:
					managePayRoll();
					break;
				case 6:
					return;
				default:
					System.out.println("Input is invalid.");
					break;
				}
				
			} catch (NumberFormatException nfe) {
				System.out.println("Input must be a natural number below " + Integer.MAX_VALUE + ".");
			}
		} while(true);
	}
	
	public void fetchEmployees() {
		employees = em.createQuery("from Employee", Employee.class).getResultList();
	}
	
	public void addEmployee() {

		Employee employee = new Employee();
		
		System.out.print("First name: ");
		employee.setFirstName(scanner.nextLine());
		
		System.out.print("Last name: ");
		employee.setLastName(scanner.nextLine());

		boolean isGenderValid = false;
		do {
			System.out.print("Gender (1.Male, 2.Female, 3.Unisex): ");
			
			switch(scanner.nextLine().charAt(0)) {
				case '1':
					employee.setGender(Employee.Gender.Male);
					isGenderValid = true;
					break;
				case '2':
					employee.setGender(Employee.Gender.Female);
					isGenderValid = true;
					break;
				case '3':
					employee.setGender(Employee.Gender.Unisex);
					isGenderValid = true;
					break;
				default:
					isGenderValid = false;
					break;
			}
		} while (!isGenderValid);
			
		
		boolean isBirthDateValid = false;
		do {
			System.out.print("Date of birth (YYYY.MM.DD): ");
			String birthDateString = scanner.nextLine();

			Pattern pattern = Pattern.compile("[0-9]*.[0-1][0-9].[0-3][0-9]");
			Matcher matcher = pattern.matcher(birthDateString);			
			
			isBirthDateValid = matcher.matches();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
			Date date;
			try {
				date = dateFormat.parse(birthDateString);
				employee.setDateOfBirth(date);
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				isBirthDateValid = false;
			}
			
			
		} while (!isBirthDateValid);

		employee.setDateJoined(new Date());
		
		boolean isPhoneNumberValid = false;
		do {
			System.out.print("Phone number: ");
			String phoneNumberString = scanner.nextLine();
			
			//min 4 digits max 15
			Pattern pattern = Pattern.compile("[0-9]{4,15}");
			Matcher matcher = pattern.matcher(phoneNumberString);
			
			isPhoneNumberValid = matcher.matches();
			
			employee.setPhoneNumber(phoneNumberString);
			
		} while (!isPhoneNumberValid);
		
		boolean isEmailValid = false;
		do {
			System.out.print("E-Mail: ");
			String eMail = scanner.nextLine();
			
			try {
				em.createQuery("from Employee e where e.eMail='" + eMail + "'", Employee.class).getSingleResult();
				isEmailValid = false;
			} catch (NoResultException nre) {
				isEmailValid = true;
				employee.seteMail(eMail);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				isEmailValid = false;
			}
			
		} while(!isEmailValid);
		
		boolean isMaritalStatusValid = false;
		do {
			System.out.print("Marital status (1.Single, 2.Married, 3.Divorced, 4.Widowed): ");
			
			switch(scanner.nextLine().charAt(0)) {
				case '1':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '2':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '3':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '4':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				default:
					isMaritalStatusValid = false;
					break;
			}
		} while (!isMaritalStatusValid);
		
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		
		fetchEmployees();
		System.out.println("Employee added.");
		System.out.println("Press enter to continue.");
		scanner.nextLine();
	}
	
	public void updateEmployee() {
		System.out.println("Please enter the employee's ID.");
		int id = Integer.parseInt(scanner.nextLine());
		
		Employee employee = em.find(Employee.class, id);
		
		if(employee == null) {
			System.out.println("No employee with ID = " + id + " found.");
			System.out.println("Press enter to continue.");
			scanner.nextLine();
			return;
		}
		
		System.out.println("To keep the previous value, simply press enter.");
		System.out.print("First name: ");
		String firstName = scanner.nextLine();
		if (!firstName.equals("")) {
			employee.setFirstName(firstName);
		}
		
		System.out.print("Last name: ");
		String lastName = scanner.nextLine();
		if(!lastName.equals("")) {
			employee.setLastName(lastName);			
		}

		boolean isGenderValid = false;
		do {
			System.out.print("Gender (1.Male, 2.Female, 3.Unisex): ");
			String gender = scanner.nextLine();
			
			if(!gender.equals("")) {
				
				switch(gender.charAt(0)) {
					case '1':
						employee.setGender(Employee.Gender.Male);
						isGenderValid = true;
						break;
					case '2':
						employee.setGender(Employee.Gender.Female);
						isGenderValid = true;
						break;
					case '3':
						employee.setGender(Employee.Gender.Unisex);
						isGenderValid = true;
						break;
					default:
						isGenderValid = false;
						break;
				}		
			} else {			
				isGenderValid = true;
			}
		} while (!isGenderValid);
			
		boolean isBirthDateValid = false;
		do {
			System.out.print("Date of birth (YYYY.MM.DD): ");
			String birthDateString = scanner.nextLine();
			
			if(!birthDateString.equals("")) {
				Pattern pattern = Pattern.compile("[0-9]*.[0-1][0-9].[0-3][0-9]");
				Matcher matcher = pattern.matcher(birthDateString);			
				
				isBirthDateValid = matcher.matches();
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date date;
				try {
					date = dateFormat.parse(birthDateString);
					employee.setDateOfBirth(date);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
					isBirthDateValid = false;
				}
			} else {
				isBirthDateValid = true;
			}
		} while (!isBirthDateValid);

		boolean isDateJoinedValid = false;
		do {
			System.out.print("Date Joined (YYYY.MM.DD): ");
			String dateJoinedString = scanner.nextLine();
			
			if(!dateJoinedString.equals("")) {
				Pattern pattern = Pattern.compile("[0-9]*.[0-1][0-9].[0-3][0-9]");
				Matcher matcher = pattern.matcher(dateJoinedString);			
				
				isDateJoinedValid = matcher.matches();
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date date;
				try {
					date = dateFormat.parse(dateJoinedString);
					employee.setDateJoined(date);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
					isDateJoinedValid = false;
				}
			} else {
				isDateJoinedValid = true;
			}
		} while (!isDateJoinedValid);
		
		boolean isDateLeftValid = false;
		do {
			System.out.print("Date Joined (YYYY.MM.DD): ");
			String dateLeftString = scanner.nextLine();
			
			if(!dateLeftString.equals("")) {
				Pattern pattern = Pattern.compile("[0-9]*.[0-1][0-9].[0-3][0-9]");
				Matcher matcher = pattern.matcher(dateLeftString);			
				
				isDateLeftValid = matcher.matches();
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
				Date date;
				try {
					date = dateFormat.parse(dateLeftString);
					employee.setDateLeft(date);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
					isDateLeftValid = false;
				}
			} else {
				isDateLeftValid = true;
			}
		} while (!isDateLeftValid);
		
		boolean isPhoneNumberValid = false;
		do {
			System.out.print("Phone number: ");
			String phoneNumberString = scanner.nextLine();
			
			if(!phoneNumberString.equals("")) {
				//min 3 digits max 15
				Pattern pattern = Pattern.compile("[0-9]{3,15}");
				Matcher matcher = pattern.matcher(phoneNumberString);
				
				isPhoneNumberValid = matcher.matches();
				
				employee.setPhoneNumber(phoneNumberString);
			} else {
				isPhoneNumberValid = true;
			}
			
		} while (!isPhoneNumberValid);
		
		boolean isEmailValid = false;
		do {
			System.out.print("E-Mail: ");
			String eMail = scanner.nextLine();
			if(!eMail.equals("")) {
				try {
					em.createQuery("from Employee e where e.eMail='" + eMail + "'", Employee.class).getSingleResult();
					isEmailValid = false;
				} catch (NoResultException nre) {
					isEmailValid = true;
					employee.seteMail(eMail);
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					isEmailValid = false;
				}
			} else {
				isEmailValid = true;
			}
			
		} while(!isEmailValid);
		
		boolean isMaritalStatusValid = false;
		do {
			System.out.print("Marital status (1.Single, 2.Married, 3.Divorced, 4.Widowed): ");
			String maritalState = scanner.nextLine();
			
			if(!maritalState.equals("")) {
				switch(maritalState.charAt(0)) {
				case '1':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '2':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '3':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				case '4':
					employee.setMaritalStatus(Employee.MaritalStatus.Single);
					isMaritalStatusValid = true;
					break;
				default:
					isMaritalStatusValid = false;
					break;
				}
			} else {
				isMaritalStatusValid = true;
			}
		} while (!isMaritalStatusValid);
		
		System.out.print("Details: ");
		String details = scanner.nextLine();
		if (!details.equals("")) {
			employee.setDetails(details);
		}
		
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
		
		fetchEmployees();
		System.out.println("Employee edited.");
		System.out.println("Press enter to continue.");
		scanner.nextLine();
	}
	
	public void deleteEmployee() {
		System.out.println("Please enter the employee's ID.");
		int id = Integer.parseInt(scanner.nextLine());
		
		Employee employee = em.find(Employee.class, id);
		
		if(employee == null) {
			System.out.println("No employee with ID = " + id + " found.");
			System.out.println("Press enter to continue.");
			scanner.nextLine();
			return;
		}
		
		em.getTransaction().begin();
		em.remove(employee);
		em.getTransaction().commit();
		
		fetchEmployees();
		System.out.println("Employee " + employee.getId() + " successfully deleted.");
		System.out.println("Press enter to continue.");
		scanner.nextLine();
	}
	
	public void managePayRoll() {
		System.out.println("Please enter the employee's ID.");
		int id = Integer.parseInt(scanner.nextLine());
		
		Employee employee = em.find(Employee.class, id);
		
		if(employee == null) {
			System.out.println("No employee with ID = " + id + " found.");
			System.out.println("Press enter to continue.");
			scanner.nextLine();
			return;
		}
		PayRollManager individualPayRollManager = new PayRollManager(em,scanner,employee);
		individualPayRollManager.GeneratePayRoll();
	}
	
	public void viewEmployees() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if(employees.isEmpty()) {
			
			stringBuilder.append("There are currently no employees.\n");
			
		} else {
			
			stringBuilder.append("Table of all employees\n");
			stringBuilder.append(String.format(".%563s.\n", "").replace(' ', '_'));
			stringBuilder.append(String.format("|%-10s|%-50s|%-50s|%-10s|%-14s|%-14s|%-14s|%-16s|%-255s|%-20s|%-100s|\n", "ID","FIRST NAME", "LAST NAME", "GENDER", "DATE_OF_BIRTH", "DATE_JOINED", "DATE_LEFT", "PHONE_NUMBER", "E_MAIL", "MARITAL STATUS", "DETAILS").replace(' ', '_'));
			for (Employee e : employees) {
		
				stringBuilder.append(String.format("|%10d|", e.getId()).replace(' ', '_'));
				stringBuilder.append(String.format("%-50s|", e.getFirstName()).replace(' ', '_'));
				stringBuilder.append(String.format("%-50s|", e.getLastName()).replace(' ', '_'));
				stringBuilder.append(String.format("%-10s|", e.getGender()).replace(' ', '_'));
				stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(e.getDateOfBirth())).replace(' ', '_'));
				stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(e.getDateJoined())).replace(' ', '_'));
				
				if(e.getDateLeft() != null) {
					stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(e.getDateLeft())).replace(' ', '_'));					
				} else {
					stringBuilder.append(String.format("%14s|", "").replace(' ', '_'));
				}
				stringBuilder.append(String.format("%16s|", e.getPhoneNumber()).replace(' ', '_'));
				stringBuilder.append(String.format("%-255s|", e.geteMail()).replace(' ', '_'));
				stringBuilder.append(String.format("%-20s|", e.getMaritalStatus()).replace(' ', '_'));	
				
				if(e.getDetails() != null) {
					stringBuilder.append(String.format("%-100s|\n", e.getDetails()).replace(' ', '_'));			
				} else {
					stringBuilder.append(String.format("%-100s|\n", "").replace(' ', '_'));
				}
			}
		}
			stringBuilder.append("Press enter to continue.");
			System.out.println(stringBuilder.toString());
			scanner.nextLine();
	}
}
