package sdpsose2018.hrms;

public class EmployeeJobAssignment {

	int employeeId;
	int jobId;
	int supervisorId;
	double startDate;
	double endDate;
	int departmentId;
	String description;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(int supervisorId) {
		this.supervisorId = supervisorId;
	}

	public double getStartDate() {
		return startDate;
	}

	public void setStartDate(double startDate) {
		this.startDate = startDate;
	}

	public double getEndDate() {
		return endDate;
	}

	public void setEndDate(double endDate) {
		this.endDate = endDate;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "EmployeeJobAssignment [employeeId=" + employeeId + ", jobId=" + jobId + ", supervisorId=" + supervisorId
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", departmentId=" + departmentId
				+ ", description=" + description + "]";
	}

	public EmployeeJobAssignment() { }
	
}