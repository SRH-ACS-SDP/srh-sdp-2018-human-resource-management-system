package sdpsose2018.hrms;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

public class JobManager {
	
	EntityManager em;
	static Scanner sc;
	List<Job> jobs;
	

	
	public JobManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
	} 


	public void addJob() {
		
		Job j = new Job();
		
		System.out.print("Enter Job Title: ");
		String jobTitle = sc.nextLine();
		j.setTitle(jobTitle);
		
		System.out.print("Enter Description: ");
		String description = sc.nextLine();
		j.setDescription(description);
		
		System.out.print("Enter Minimum Salary: ");
		double minSalary = Double.parseDouble(sc.nextLine());
		j.setMinSalary(minSalary);
		
		System.out.print("Enter Maximum Salary: ");
		double maxSalary = Double.parseDouble(sc.nextLine());
		j.setMaxSalary(maxSalary);
		
		System.out.print("Enter Skills Required: ");
		String skillRequired = sc.nextLine();
		j.setSkillsRequired(skillRequired);
		
		em.getTransaction().begin();
		em.persist(j);
		em.getTransaction().commit();
		fetchJob();
		System.out.println("Job has been added successfully!!!!");
	
	}
	
	public void fetchJob() {
		jobs = em.createQuery("from Job", Job.class).getResultList();
	}
	
	public void menuForJob() {
		
		boolean is_valid  =  true;
		int number = 0;
	
		System.out.println("\n_____Job_Module_____\n");
	do {
		System.out.println("\n1. Add a Job ");
		System.out.println("2. Go back to main menu.");
		try {
			System.out.print("\nEnter Number: ");
			number  =  Integer.parseInt(sc.nextLine());
		
			switch(number) {
		
				case 1 :addJob();
						whatNext();
						is_valid = false;
						break;
						
				case 2: return;
						
				default: System.out.println("Invalid Number. Try again ! ");
						is_valid = true;
			}	
		}catch (Exception e) {
			System.out.println("Please Enter Valid Number! ");
			is_valid = true;
		}
		}while(is_valid);
	}
	
public void whatNext(){
		
		boolean is_valid  =  true;
		do {
			try {
				System.out.print("\nDo you want to goto the Job Manager Menu ? (y/n):");
				char c = sc.nextLine().charAt(0);
		
					if(c == 'y') {
						menuForJob();
						is_valid = false;
					}
					else if(c == 'n') {
						System.out.println("Thank you");
						is_valid = false;
					}else {
						System.out.println("Invalid Entry. Try again ! ");
						is_valid = true;
					}
			}catch(Exception e){
				System.out.println("Invalid Entry. Try again ! ");
				is_valid = true;
			}
	}while(is_valid);
	}
		
		
	}

