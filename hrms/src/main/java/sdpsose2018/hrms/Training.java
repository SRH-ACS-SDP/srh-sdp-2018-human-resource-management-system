package sdpsose2018.hrms;

public class Training {

	int id;
	String employeeId;
	int courseId;
	int conductorId;
	double date;
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

	public double getDate() {
		return date;
	}

	public void setDate(double date) {
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
