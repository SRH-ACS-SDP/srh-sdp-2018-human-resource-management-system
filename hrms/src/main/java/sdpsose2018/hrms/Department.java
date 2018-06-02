package sdpsose2018.hrms;

public class Department {

	int id;
	String name;
	String address;
	String description;
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

	public Department(){ }
	
}
