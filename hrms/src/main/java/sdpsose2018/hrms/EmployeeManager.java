package sdpsose2018.hrms;

import java.sql.Timestamp;
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
			System.out.print("Gender (m/f): ");
			
			switch(scanner.nextLine().charAt(0)) {
				case 'm':
					employee.setGender(Employee.Gender.Male);
					isGenderValid = true;
					break;
				case 'f':
					employee.setGender(Employee.Gender.Female);
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
			long unixTime = 0;
			try {
				date = dateFormat.parse(birthDateString);
				unixTime = (long) date.getTime()/1000;
			} catch (ParseException e) {
				System.out.println(e.getMessage());
				isBirthDateValid = false;
			}
			
			employee.setDateOfBirth((int)unixTime);
			
		} while (!isBirthDateValid);

		employee.setDateJoined((int) new Date().getTime()/1000);
		
		boolean isPhoneNumberValid = false;
		do {
			System.out.print("Phone number: ");
			String phoneNumberString = scanner.nextLine();
			
			//min 4 digits max 15
			Pattern pattern = Pattern.compile("[0-9]{4,15}");
			Matcher matcher = pattern.matcher(phoneNumberString);
			
			isPhoneNumberValid = matcher.matches();
			
			employee.setPhoneNumber(Integer.parseInt(phoneNumberString));
			
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
				employee.eMail = eMail;
				
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
	}
	
	public void viewEmployees() {
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Table of all employees\n");
		stringBuilder.append(String.format(".%258s.\n", "").replace(' ', '_'));
		stringBuilder.append(String.format("|%-10s|%-25s|%-25s|%-10s|%-14s|%-14s|%-14s|%-16s|%-50s|%-20s|%-50s|\n", "ID","FIRST NAME", "LAST NAME", "GENDER", "DATE_OF_BIRTH", "DATE_JOINED", "DATE_LEFT", "PHONE_NUMBER", "E_MAIL", "MARITAL STATUS", "DETAILS").replace(' ', '_'));
		for (Employee e : employees) {
			
			stringBuilder.append(String.format("|%10d|", e.getId()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", e.getFirstName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-25s|", e.getLastName()).replace(' ', '_'));
			stringBuilder.append(String.format("%-10s|", e.getGender()).replace(' ', '_'));
			
			Timestamp birthDate = new Timestamp(e.getDateOfBirth()*1000L);
			stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(birthDate)).replace(' ', '_'));

			Timestamp dateJoined = new Timestamp(e.getDateJoined()*1000L);
			stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(dateJoined)).replace(' ', '_'));

			Timestamp dateLeft = new Timestamp(e.getDateLeft()*1000L);
			stringBuilder.append(String.format("%14s|", new SimpleDateFormat("yyyy.MM.dd").format(dateLeft)).replace(' ', '_'));

			stringBuilder.append(String.format("%16d|", e.getPhoneNumber()).replace(' ', '_'));
			stringBuilder.append(String.format("%-50s|", e.geteMail()).replace(' ', '_'));
			stringBuilder.append(String.format("%-20s|", e.getMaritalStatus()).replace(' ', '_'));			
			stringBuilder.append(String.format("%-50s|\n", e.getDetails()).replace(' ', '_'));			
			
		}
		
		System.out.println(stringBuilder.toString());
	}
}
