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
		
		//System.out.println("Employee ID: " + e.id );
		//System.out.println("Employee Full Name: " + e.firstName + " " + e.lastName);
		System.out.println("\n\n====================PAY-ROLL-MANAGER====================");
		/*System.out.println("enter the Employee ID: ");
		String name =sc.nextLine();*/
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
	
		
	/*	System.out.println("enter the Employee ID: ");
		int id = Integer.parseInt(sc.nextLine());
		Query query  = em.createQuery("Select id from Employee e where e.id LIKE :id");
		query.setParameter("id", id);
		List<Integer> ids   = query.getResultList();
		String employeeName = e.firstName;
		System.out.println(employeeName);
		
		Query query1  = em.createQuery("Select id from Department d where d.id LIKE :id");
		query1.setParameter("id", id);
		List ids1   = query1.getResultList();
		String department = d.name;
		System.out.println(department);
		
		Query query2  = em.createQuery("Select salary from EmployeeJobAssignment eja where eja.salary LIKE :salary");
		query2.setParameter("id", id);
		List ids2   = query2.getResultList();
		EmployeeJobAssignment eja1 = em.find(EmployeeJobAssignment.class, ids2.get(0));
		double salary = eja.salary;
		System.out.println(salary);*/
		
		/*
		Query departmentIds = em.createNativeQuery("SELECT id FROM employees_jobs_assignments WHERE employee_id = " + id);
		query.setParameter("id", id);
		List<Integer> countryids   = query.getResultList();
		int departmentIds = e.;*/
		
		//System.out.println("enter CountryId: ");
		int countryId = Integer.parseInt (sc.nextLine());
		//query.setParameter("id", countryId);
	//	List<Integer> countryids   = query.getResultList();
		
		
		
		Query jobIds  = em.createQuery("Select id from Country c where c.id LIKE :countryId");
		System.out.println("enter Salary: ");
		
		
		
		
		
		
		//netSalary = grossSalary - ((grossSalary*taxrate) / 100);
		
		//System.out.println(netSalary);
		
		//System.out.println(Enter);
		return;
		
		
		/*
        PayRoll data = new PayRoll();
 
        System.out.println("Enter Employee name: ");
        data.setName(sc.nextLine());
 
        System.out.println("Number of Hours Worked: ");
        data.setHoursWorked(Double.parseDouble(sc.nextLine()));
 
        System.out.println("Hourly Pay Rate: ");
        data.setHourlyPayRate(Double.parseDouble(sc.nextLine()));
 
        double grossAmount = data.grossPay(data.getHourlyPayRate(), data.getHoursWorked());
 
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println("Name: " + data.getName());
        System.out.println("Hours Worked: " + data.getHoursWorked() + "hrs");
        System.out.println("Pay Rate: $" + data.getHourlyPayRate());
        System.out.println("Gross Pay: $" + grossAmount + '\n');
        System.out.println("DEDUCTIONS");
        System.out.println("Federal Tax Withholding (20%): $" + data.federalTax(grossAmount));
        System.out.println("State Tax Withholding (9%): $" + data.stateTax(grossAmount));
        System.out.println("Total Deductions: $" + (data.federalTax(grossAmount) + data.stateTax(grossAmount)));
        System.out.println("Net Pay: $" + (grossAmount - (data.federalTax(grossAmount) + data.stateTax(grossAmount))));
 		*/
    }
}
