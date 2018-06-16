package sdpsose2018.hrms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import sdpsose2018.hrms.Employee.Gender;

public class PayRollManager {

	EntityManager em;
	Scanner sc;
	Employee e;
	
	public PayRollManager(EntityManager em, Scanner sc, int employeeId) {
		
		this.em = em;
		this.sc = sc;
		this.e = em.find(Employee.class, employeeId);
		
	}
	
	public void GeneratePayRoll() {
		
		Country c;
		Location l;
		Department d;
		EmployeeJobAssignment eja;
		
		Query s = em.createNativeQuery("SELECT * FROM employees_jobs_assignments WHERE employee_id = " + e.id);
		List<Object[]> objects = s.getResultList();
		Object[] test = objects.get(0);
		EmployeeJobAssignment result = new EmployeeJobAssignment();
		result.employeeId = (Integer) test[0];
		result.jobId = (Integer) test[1];
		result.supervisorId = (Integer) test[2];
		result.departmentId = (Integer) test[3];
		result.startDate = (Date) test[4];
		result.endDate = (Date) test[5];
		result.salary = (Double) test[6];
		result.description = (String) test[7];
		
		eja = result;
		
		d = em.find(Department.class, eja.getDepartmentId());
		l = em.find(Location.class, d.locationId);
		c = em.find(Country.class, l.countryId);
		double netSalary;
		
		
		System.out.println("\n\n====================PAY-ROLL-MANAGER====================");
		
		if(e.gender==Gender.Male) {
			
		System.out.println("\nEmployee Full Name:\t" +"MR "+ e.firstName + " " + e.lastName);
		
		}
		if (e.gender==Gender.Female) {
			System.out.println("\nEmployee Full Name:\t" +"Miss "+ e.firstName + " " + e.lastName);
		}
		System.out.println("Working since:\t\t" +new SimpleDateFormat("yyyy.MM.dd").format(e.dateJoined));
		System.out.println("Department:\t\t" + d.name);
		System.out.println("Location:\t\t" + l.name );
		System.out.println("Country:\t\t" + c.name); 
		System.out.println("Mobile #:\t\t" + e.phoneNumber);
		System.out.println("Salary for the month:\t"+new SimpleDateFormat("yyyy, MMMM").format(new Date()));
		System.out.println("Basic Salary:\t\t" + eja.salary + " EUR");
		System.out.println("Tax Rate:\t\t" + c.taxRate + "%");
		netSalary = eja.salary - ((eja.salary*c.getTaxRate() / 100));
		System.out.println("Net Salary:\t\t" + netSalary + " EUR");
	
		Query jobIds  = em.createQuery("Select id from Country c where c.id LIKE :countryId");
		System.out.println("enter Salary: ");
		
		return;
		
		
		    }
}
