/**
 *
 */
package sdpsose2018.hrms;


public class Skill {
	
	 int id;
	 String name;
	 String description;
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

