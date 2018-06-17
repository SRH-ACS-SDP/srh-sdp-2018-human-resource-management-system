package sdpsose2018.hrms;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.LayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.util.SortUtils;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public abstract class DynamicJasperReport {

    public Map getParams() {
        return params;
    }

    protected JasperPrint jp;
    protected JasperReport jr;
    protected final Map<String, Object> params = new HashMap<String, Object>();
    protected DynamicReport dr;
    

    public abstract DynamicReport buildPayRollReport() throws Exception;
    
    public abstract DynamicReport buildPaySlipReport() throws Exception;

    public void generatePayRollReport() throws Exception {
        dr = buildPayRollReport();

			/*
              Get a JRDataSource implementation
			 */
        JRDataSource ds = getPayRollDataSource();
		/*
			  Creates the JasperReport object, we pass as a Parameter
			  the DynamicReport, a new ClassicLayoutManager instance (this
			  one does the magic) and the JRDataSource
			 */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

			/*
			  Creates the JasperPrint object, we pass as a Parameter
			  the JasperReport object, and the JRDataSource
			 */
        System.out.println("Filling the report");
        if (ds != null)
            jp = JasperFillManager.fillReport(jr, params, ds);
        else
            jp = JasperFillManager.fillReport(jr, params);

        System.out.println("Exporting the report (pdf)");
        exportPayRollReport();

        System.out.println("Export successfully completed");

    }
    
    public void generatePaySlipReport(int empdID) throws Exception {
        dr = buildPaySlipReport();

			/*
              Get a JRDataSource implementation
			 */
        JRDataSource ds = getPaySlipDataSource(empdID);
		/*
			  Creates the JasperReport object, we pass as a Parameter
			  the DynamicReport, a new ClassicLayoutManager instance (this
			  one does the magic) and the JRDataSource
			 */
        jr = DynamicJasperHelper.generateJasperReport(dr, getLayoutManager(), params);

			/*
			  Creates the JasperPrint object, we pass as a Parameter
			  the JasperReport object, and the JRDataSource
			 */
        System.out.println("Filling the report");
        if (ds != null)
            jp = JasperFillManager.fillReport(jr, params, ds);
        else
            jp = JasperFillManager.fillReport(jr, params);

        System.out.println("Exporting the report (pdf)");
        exportPaySlipReport();

        System.out.println("Export successfully completed");

    }

    protected LayoutManager getLayoutManager() {
        return new ClassicLayoutManager();
    }

    protected void exportPayRollReport() throws Exception {
        ReportExporter.exportReport(jp, Constants.file_directory_payroll);
    }
    
    
    protected void exportPaySlipReport() throws Exception {
        ReportExporter.exportReport(jp, Constants.file_directory_payslip);
    }

    /**
     * @return JRDataSource
     */
    protected JRDataSource getPayRollDataSource() {
    	    PayRollReportManager prs = new PayRollReportManager();    
    	
        Collection<PayRollReport> payRollCollection = prs.getAllSalary();
        payRollCollection = SortUtils.sortCollection(payRollCollection, dr.getColumns());
        
        return new JRBeanCollectionDataSource(payRollCollection);
    }
    
    
    /**
     * @return JRDataSource
     */
    protected JRDataSource getPaySlipDataSource(int empID) {
    	    PayRollReportManager prs = new PayRollReportManager();    
    	
        Collection<PaySlipReport> paySlipCollection = prs.getSalaryEmployee(empID);
        paySlipCollection = SortUtils.sortCollection(paySlipCollection, dr.getColumns());
        
        return new JRBeanCollectionDataSource(paySlipCollection);
    }

    public DynamicReport getDynamicReport() {
        return dr;
    }
}
