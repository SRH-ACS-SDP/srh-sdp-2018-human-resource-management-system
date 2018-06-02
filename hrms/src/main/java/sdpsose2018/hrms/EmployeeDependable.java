package sdpsose2018.hrms;

public class EmployeeDependable {

	int employeeId;
	double dependableBirthdate;
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public double getDependableBirthdate() {
		return dependableBirthdate;
	}

	public void setDependableBirthdate(double dependableBirthdate) {
		this.dependableBirthdate = dependableBirthdate;
	}

	@Override
	public String toString() {
		return "EmployeeDependable [employeeId=" + employeeId + ", dependableBirthdate=" + dependableBirthdate + "]";
	}

	public EmployeeDependable(){ }
	
}
