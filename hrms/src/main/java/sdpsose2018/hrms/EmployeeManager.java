package sdpsose2018.hrms;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;

public class EmployeeManager {

	EntityManager em;
	List<Employee> employees;
	
	public EmployeeManager(EntityManager em) {
		
		this.em = em;
		
		employees = em.createQuery("from Employee", Employee.class).getResultList();
		
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
			stringBuilder.append(String.format("%-50s|\n", e.getDetails()));			
			
		}
		
		System.out.println(stringBuilder.toString());
	}
}
