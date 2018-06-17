package sdpsose2018.hrms;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

public class SkillManager {

	
	EntityManager em;
	static Scanner sc;
	List<Skill> skills;
	

	
	public SkillManager(EntityManager em, Scanner sc) {
		this.em = em;
		this.sc = sc;
		
	} 
	
public void addJob() {
		
		Skill s = new Skill();
		
		System.out.print("Enter Skill Name: ");
		String skillName = sc.nextLine();
		s.setName(skillName);
		
		System.out.print("Enter Description: ");
		String description = sc.nextLine();
		s.setDescription(description);
		
		System.out.print("Enter Difficult Level: ");
		int difficult = Integer.parseInt(sc.nextLine());
		s.setDifficulty(difficult);
	
		em.getTransaction().begin();
		em.persist(s);
		em.getTransaction().commit();
		fetchSkill();
		System.out.println("Skill has been added successfully!!!!");
	
	}

public void fetchSkill() {
	skills = em.createQuery("from Skill", Skill.class).getResultList();
}
	

public void menuForJob() {
	
	boolean is_valid  =  true;
	int number = 0;

	System.out.println("\n_____Skill_Module_____\n");
do {
	System.out.println("\n1. Add a Skill ");
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
			System.out.print("\nDo you want to goto the Skill Manager Menu ? (y/n):");
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
