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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateJoined() {
		return dateJoined;
	}

	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	public Date getDateLeft() {
		return dateLeft;
	}

	public void setDateLeft(Date dateLeft) {
		this.dateLeft = dateLeft;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", dateOfBirth=" + dateOfBirth + ", dateJoined=" + dateJoined + ", dateLeft=" + dateLeft
				+ ", phoneNumber=" + phoneNumber + ", eMail=" + eMail + ", maritalStatus=" + maritalStatus
				+ ", details=" + details + "]";
	}

	public Employee() { }
	
	public enum Gender { Male,Female,Unisex }
	
	public enum MaritalStatus { Single, Married, Divorced, Widowed }
}
