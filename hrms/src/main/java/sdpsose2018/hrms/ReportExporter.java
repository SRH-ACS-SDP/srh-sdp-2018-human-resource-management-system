package sdpsose2018.hrms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class ReportExporter {
   
    /**
     * The path to the file must exist.
     *
     * @param jp
     * @param path
     * @throws JRException
     * @throws IOException 
     */
    public static void exportReport(JasperPrint jp, String path) throws JRException, IOException {
        
        JRPdfExporter exporter = new JRPdfExporter();

        File outputFile = new File(path);
        File parentFile = outputFile.getParentFile();
        if (parentFile != null)
            parentFile.mkdirs();
        FileOutputStream fos = new FileOutputStream(outputFile);

        SimpleExporterInput simpleExporterInput = new SimpleExporterInput(jp);
        OutputStreamExporterOutput simpleOutputStreamExporterOutput = new SimpleOutputStreamExporterOutput(fos);

        exporter.setExporterInput(simpleExporterInput);
        exporter.setExporterOutput(simpleOutputStreamExporterOutput);

        exporter.exportReport();
        
        simpleOutputStreamExporterOutput.close();
        fos.close();
        
    }

}
