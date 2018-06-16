package sdpsose2018.hrms;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="employees")
public class Employee {
	
	@Id
	@Column(name = "employee_id")
	int id;
	
	@Column(name = "first_name")
	String firstName;
	
	@Column(name = "last_name")
	String lastName;
	
	@Enumerated(EnumType.STRING)
	Gender gender;
	
	@Column(name = "date_of_birth")
	Date dateOfBirth;
	
	@Column(name = "date_joined")
	Date dateJoined;
	
	@Column(name = "date_left")
	Date dateLeft;
	
	@Column(name = "phone_number")
	String phoneNumber;
	
	@Column(name = "e_mail")
	String eMail;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "marital_status")
	MaritalStatus maritalStatus;
	
	@Column(name = "details")
	String details;
	
	public Employee() { }
	
	public enum Gender { Male,Female }
	
	public enum MaritalStatus { Single, Married, Divorced, Widowed }
}
