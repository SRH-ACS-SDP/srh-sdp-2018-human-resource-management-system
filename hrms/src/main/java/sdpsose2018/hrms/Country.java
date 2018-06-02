package sdpsose2018.hrms;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country {

	@Id
	int id;
	String name;
	String language;
	String currency;
	
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", language=" + language + ", currency=" + currency + "]";
	}

	public Country(){ }
	
}
