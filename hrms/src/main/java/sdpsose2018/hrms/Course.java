package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@Column(name = "course_id")
	int id;
	
	String name;
	
	String description;
	
	@Column(name = "required_skills")
	String requiredSkills;
	
	@Column(name = "acquired_skills")
	String acquiredSkills;
	
	@Column(name = "mentor_skills")
	String mentorSkills;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRequiredSkills() {
		return requiredSkills;
	}

	public void setRequiredSkills(String requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public String getAcquiredSkills() {
		return acquiredSkills;
	}

	public void setAcquiredSkills(String acquiredSkills) {
		this.acquiredSkills = acquiredSkills;
	}

	public String getMentorSkills() {
		return mentorSkills;
	}

	public void setMentorSkills(String mentorSkills) {
		this.mentorSkills = mentorSkills;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", description=" + description + ", requiredSkills="
				+ requiredSkills + ", acquiredSkills=" + acquiredSkills + ", mentorSkills=" + mentorSkills + "]";
	}
	
	public Course() { }
	
}
