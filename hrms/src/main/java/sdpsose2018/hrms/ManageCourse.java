package sdpsose2018.hrms;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.mysql.cj.jdbc.PreparedStatement;



public class ManageCourse {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
	EntityManager em = emf.createEntityManager();
	
	
	public void addCourse() {
		
		Scanner sc = new Scanner(System.in);
	
		Course co = new Course();
	
	
		System.out.print("Course name: ");
		String coursename = sc.next();
		co.setName(coursename);
	
		System.out.print("Acquired Skills: ");
		String AS = sc.next();
		co.setAcquiredSkills(AS);
	
		System.out.print("Description: ");
		String De = sc.next();
		co.setDescription(De);
	
		System.out.print("Mentor Skills: ");
		String MS = sc.next();
		co.setMentorSkills(MS);
	
		System.out.print("Required Skills: ");
		String RS = sc.next();
		co.setRequiredSkills(RS);
	
		em.getTransaction().begin();
		em.persist(co);
		em.getTransaction().commit();
	

		System.out.println(co);
}
	
	public void viewCourse() {
		
		
		
		 String query = "SELECT * FROM courses";
		 System.out.println(query);

		/*for (int i = 1; i < 7 ; i++) {

			Course co = em.find(Course.class, i);
			System.out.println(co);
		}*/
//		try
//	    {
//
//	    String query = "SELECT * FROM courses";
//	   //  rs = stmt.executeQuery(query);
//	    List<Course> rs = em.createQuery("SELECT * FROM courses", Course.class).getResultList();
//	    Iterator<rs> it=query.iterator();
//		while (rs.isEmpty()) {
//			
//			String name = rs.get("NAME");
//			String description = rs.getString("DESCRIPTION");
//			String requiredskills = rs.getString("REQUIRED_SKILLS");
//			String acquiredskills = rs.getString("ACQUIRED_SKILLS");
//			String mentorskills = rs.getString("MENTOR_SKILLS");
//			
//
//			
//			System.out.format("%s, %s, %s, %s\n", name, description, requiredskills, acquiredskills, mentorskills);
//		}
//		
//	    }catch (Exception e)
//	    {
//	        System.err.println("Got an exception! ");
//	        System.err.println(e.getMessage());
//	      }
//		
//		em.getTransaction().begin();
//		em.close();
//		
//	}
		
		 
		
}
}
