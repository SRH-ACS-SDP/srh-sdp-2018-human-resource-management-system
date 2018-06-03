package sdpsose2018.hrms;

import javax.persistence.*;

@Entity
@Table(name="departments")
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "department_id")
	int id;
	
	String name;
	
	String address;
	
	String description;
	
	@JoinColumn(name = "location_id")
	@Column(name = "location_id")
	int locationId;
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", address=" + address + ", description=" + description
				+ ", locationId=" + locationId + "]";
	}

	public Department() { }
	
}
