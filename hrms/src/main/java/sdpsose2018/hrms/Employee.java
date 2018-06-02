package sdpsose2018.hrms;

public class Employee {
	
	int id;
	String firstName;
	String lastName;
	Gender gender;
	double dateOfBirth;
	double dateJoined;
	double dateLeft;
	int phoneNumber;
	String eMail;
	MaritalStatus maritalStatus;
	String details;
	
	public Employee() { }
	
	public enum Gender { Male,Female }
	
	public enum MaritalStatus { Single, Married, Divorced, Widowed }
}
