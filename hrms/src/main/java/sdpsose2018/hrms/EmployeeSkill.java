package sdpsose2018.hrms;

public class EmployeeSkill {

	int employeeId;
	int skillId;
	int trainingId;
	
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public int getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	@Override
	public String toString() {
		return "EmployeeSkill [employeeId=" + employeeId + ", skillId=" + skillId + ", trainingId=" + trainingId + "]";
	}

	public EmployeeSkill() { }
	
}
