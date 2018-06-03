package sdpsose2018.hrms;

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
	int dateOfBirth;
	
	@Column(name = "date_joined")
	int dateJoined;
	
	@Column(name = "date_left")
	int dateLeft;
	
	@Column(name = "phone_number")
	int phoneNumber;
	
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
