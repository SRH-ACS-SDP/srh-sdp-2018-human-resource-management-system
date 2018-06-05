package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name = "trainings")
public class Training {

	@Id
	@Column(name = "training_id")
	int id;
	
	@JoinColumn(name = "employee_id")
	@Column(name = "employee_id")
	String employeeId;
	
	@JoinColumn(name = "course_id")
	@Column(name = "course_id")
	int courseId;
	
	@JoinColumn(name = "employee_id")
	@Column(name = "conductor_id")
	int conductorId;
	
	int date;
	
	String result;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getConductorId() {
		return conductorId;
	}

	public void setConductorId(int conductorId) {
		this.conductorId = conductorId;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Training [id=" + id + ", employeeId=" + employeeId + ", courseId=" + courseId + ", conductorId="
				+ conductorId + ", date=" + date + ", result=" + result + "]";
	}

	public Training() { }
	
}
