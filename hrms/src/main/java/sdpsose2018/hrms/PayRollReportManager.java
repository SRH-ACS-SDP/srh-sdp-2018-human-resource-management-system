package sdpsose2018.hrms;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;

public class PayRollReportManager extends DynamicJasperReport {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
 	private EntityManager em = emf.createEntityManager(); 	
	
	

	public List<PayRollReport> getAllSalary(){
		
		List<PayRollReport> col =  new ArrayList<PayRollReport>();
     	
     	List<Object[]> resultset = (List<Object[]>)em.createQuery("select e.id,e.firstName,e.lastName,ejs.salary,c.taxRate,c.currency from Employee e,EmployeeJobAssignment ejs,Department d,Location ls,Country c  where  e.id = ejs.employeeId and ejs.departmentId = d.id and d.locationId = ls.id and ls.countryId = c.id", Object[].class).getResultList();
  
     	for (Object[] result : resultset) {
     		
     		PayRollReport prp =new PayRollReport();
     		prp.setEmployeeid((Integer)result[0]);
     		
     		StringBuilder full_name = new StringBuilder();
     		full_name.append((String)result[1]).append(" ").append((String)result[2]);
     		prp.setEmployeename(full_name.toString());
     		prp.setGross_salary(String.valueOf((Double)result[3]).concat(" ").concat((String)result[5]));
     		prp.setTax((Double)result[4]);
     		
     		double netSalary = PayRollUtil.calculateNetSalary((Double)result[3],(Double)result[4]);
     		prp.setNet_salary((String.valueOf(netSalary)).concat(" ").concat((String)result[5]));
     	
     		col.add(prp);	
     	}	
		return col;
	}
	
	public List<PaySlipReport> getSalaryEmployee(int empID){
		
         List<PaySlipReport> col =  new ArrayList<PaySlipReport>();
         
      
     	
        Employee e;
     	Country c;
		Location l;
		Department d;
		EmployeeJobAssignment eja;
		
		e = em.find(Employee.class, empID);
		
		Query s = em.createNativeQuery("SELECT * FROM employees_jobs_assignments WHERE employee_id = " + empID);
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
		
		PaySlipReport psp = new PaySlipReport();
		
		psp.setEmployeeId(empID);
		psp.setEmployeeName(e.firstName.concat(" ").concat(e.lastName));
		psp.setCountry(c.name);
		psp.setDepartment(d.name);
		psp.setLocation(l.name);
		psp.setGender(e.gender.toString());
		psp.setGrossSalary(String.valueOf(eja.getSalary()).concat(" ").concat(c.currency));
		psp.setMobile(String.valueOf(e.phoneNumber));
		psp.setTaxRate(c.taxRate);
		double netSalary =PayRollUtil.calculateNetSalary(eja.getSalary(),c.getTaxRate());
		psp.setNetSalary((String.valueOf(netSalary)).concat(" ").concat((c.currency)));
		psp.setWorkSince(new SimpleDateFormat("yyyy.MM.dd").format(e.dateJoined));
		
		col.add(psp);
		return col;
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	public DynamicReport buildPayRollReport() throws Exception {
		
		Style titleStyle = new Style("titleStyle");
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(Font.GEORGIA_BIG_BOLD);
		
		Style headerStyle = new Style();

		headerStyle.setBackgroundColor(Color.orange);
		headerStyle.setBorderBottom(Border.THIN());
		headerStyle.setBorderColor(Color.black);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setTransparency(Transparency.OPAQUE);
		
		Style subtitleStyleParent = new Style("subtitleParent");
		subtitleStyleParent.setBackgroundColor(new Color(230, 230, 230));
		subtitleStyleParent.setTransparency(Transparency.OPAQUE);
		
		Style subtitleStyle = Style.createBlankStyle("subtitleStyle","subtitleParent");
		subtitleStyle.setFont(Font.GEORGIA_SMALL_BOLD);
		
		Style columnStyle = new Style(); 
		columnStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		
		FastReportBuilder drb = new FastReportBuilder();
		 drb.addColumn("Employee ID", "employeeid", Integer.class.getName(),100,false,null,columnStyle)
			.addColumn("Employee Name", "employeename", String.class.getName(),120,false,null,columnStyle)			
			.addColumn("Gross Salary", "gross_salary", String.class.getName(),130,true,null,columnStyle)
			.addColumn("Tax(%)", "tax", Double.class.getName(),60,true,"0.00",columnStyle)
			.addColumn("Net Salary", "net_salary", String.class.getName(),60,false,null,columnStyle)
			.setTitle("Payroll Report of Employees")
			.setSubtitle("This report was generated on " + new Date())
			.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, null)
			.addStyle(subtitleStyleParent)
			.setUseFullPageWidth(true);
		
		DynamicReport dr = drb.build();

		return dr;
	}
	
	
	@Override
	public DynamicReport buildPaySlipReport() throws Exception {
		Style titleStyle = new Style("titleStyle");
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(Font.GEORGIA_BIG_BOLD);
		
		Style headerStyle = new Style();

		headerStyle.setBackgroundColor(Color.orange);
		headerStyle.setBorderBottom(Border.THIN());
		headerStyle.setBorderColor(Color.black);
		headerStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		headerStyle.setTransparency(Transparency.OPAQUE);
		
		Style subtitleStyleParent = new Style("subtitleParent");
		subtitleStyleParent.setBackgroundColor(new Color(230, 230, 230));
		subtitleStyleParent.setTransparency(Transparency.OPAQUE);
		
		Style subtitleStyle = Style.createBlankStyle("subtitleStyle","subtitleParent");
		subtitleStyle.setFont(Font.GEORGIA_SMALL_BOLD);
		
		Style columnStyle = new Style(); 
		columnStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		
		FastReportBuilder drb = new FastReportBuilder();
		 drb.addColumn("EmployeeID", "employeeId", Integer.class.getName(),60,true,null,columnStyle)
			.addColumn("Employee Name", "employeeName", String.class.getName(),80,true,null,columnStyle)
			.addColumn("Department", "department", String.class.getName(),70,true,null,columnStyle)
			.addColumn("Gender", "gender", String.class.getName(),40,true,null,columnStyle)
			.addColumn("Mobile", "mobile", String.class.getName(),50,true,null,columnStyle)
			.addColumn("Location", "location", String.class.getName(),70,true,null,columnStyle)
			.addColumn("Gross Salary", "grossSalary", String.class.getName(),70,true,null,columnStyle)
			.addColumn("Tax(%)", "taxRate", Double.class.getName(),50,true,"0.00",columnStyle)
			.addColumn("Net Salary", "netSalary", String.class.getName(),50,false,null,columnStyle)
			.setTitle("PaySip of the Month "+new SimpleDateFormat("MMMM, yyyy").format(new Date()))
			.setSubtitle("This report was generated on " + new Date())
			.setDefaultStyles(titleStyle, subtitleStyle, headerStyle, null)
			.addStyle(subtitleStyleParent)
			.setUseFullPageWidth(true);
	    	 
	    	
		DynamicReport dr = drb.build();

		return dr;
	}
	
	public static void main( String[] args ) throws Exception
	
    {
     PayRollReportManager prs = new PayRollReportManager();
     
     prs.generatePayRollReport();
     
     prs.generatePaySlipReport(181);
    }

	
}
