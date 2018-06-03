package sdpsose2018.hrms;

import javax.persistence.*;

@Table(name = "employees_skills")
public class EmployeeSkill {

	@JoinColumn(name = "employee_id")
	@Column(name = "employee_id")
	int employeeId;
	
	@JoinColumn(name = "skill_id")
	@Column(name = "skill_id")
	int skillId;
	
	@JoinColumn(name = "training_id")
	@Column(name = "training_id")
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
