package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
public class EmployeeDependable {

	@JoinColumn(name = "location_id")
	@Column(name = "employee_id")
	int employeeId;
	
	@Column(name = "birthdate")
	int birthDate;
	

	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}


	public int getBirthDate() {
		return birthDate;
	}


	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "EmployeeDependable [employeeId=" + employeeId + ", birthDate=" + birthDate + "]";
	}

	public EmployeeDependable() { }
	
}
