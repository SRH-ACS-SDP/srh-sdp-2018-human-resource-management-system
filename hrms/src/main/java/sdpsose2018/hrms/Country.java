package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name = "countries")
public class Country {

	@Id
	@Column(name = "country_id")
	int id;
	
	@Column(name = "name")
	String name;
	
	String language;
	
	String currency;
	
	@Column(name = "tax_rate")
	Double taxRate;
	
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

	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	
	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", language=" + language + ", currency=" + currency
				+ ", taxRate=" + taxRate + "]";
	}

	public Country() { }
	
}
