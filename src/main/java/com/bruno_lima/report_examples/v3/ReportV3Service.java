package com.bruno_lima.report_examples.v3;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportV3Service {
    @Autowired
    private DataSource dataSource;

    private final String TEMPLATE = "reports/reportV3.jrxml";

    public byte[] generateReportInBytes() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", "BRuno");

        String path = ResourceUtils.getFile("classpath:" + TEMPLATE).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, this.dataSource.getConnection());
        return exportJasperReportBytes(jasperPrint);
    }

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
}
