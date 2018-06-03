package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name = "employees_jobs_assignments")
public class EmployeeJobAssignment {

	@JoinColumn(name = "employee_id")
	@Column(name = "employee_id")
	int employeeId;
	
	@JoinColumn(name = "job_id")
	@Column(name = "job_id")
	int jobId;
	
	@JoinColumn(name = "employee_id")
	@Column(name = "supervisor_id")
	int supervisorId;
	
	@JoinColumn(name = "department_id")
	@Column(name = "department_id")
	int departmentId;
	
	@Column(name = "start_date")
	int startDate;
	
	@Column(name = "end_date")
	int endDate;
	
	double salary;
	
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

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getStartDate() {
		return startDate;
	}

	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
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
				+ ", departmentId=" + departmentId + ", startDate=" + startDate + ", endDate=" + endDate + ", salary="
				+ salary + ", description=" + description + "]";
	}

	public EmployeeJobAssignment() { }
	
}