package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name = "skills")
public class Skill {
	
	@Id
	@Column(name = "skill_id")
	int id;
	
	String name;
	
	String description;
	
	@Column(name = "DIFFICULT")
	int difficulty;

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

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	@Override
	public String toString() {
		return "Skill [id=" + id + ", name=" + name + ", description=" + description + ", difficulty=" + difficulty
				+ "]";
	}

	public Skill() { }
	
}

