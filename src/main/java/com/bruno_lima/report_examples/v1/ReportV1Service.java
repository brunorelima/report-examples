package com.bruno_lima.report_examples.v1;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class ReportV1Service {
    private final String TEMPLATE = "reports/reportV1.jrxml";

    public byte[] generateReportInBytes() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", "BRuno");

        String path = ResourceUtils.getFile("classpath:" + TEMPLATE).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
        return exportJasperReportBytes(jasperPrint);
    }

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
}
