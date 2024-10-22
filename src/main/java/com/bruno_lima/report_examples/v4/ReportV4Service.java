package com.bruno_lima.report_examples.v4;

import com.bruno_lima.report_examples.v4.data.CountryRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportV4Service {
    @Autowired
    private CountryRepository countryRepository;

    private final String TEMPLATE = "reports/reportV4.jrxml";

    public byte[] generateReportInBytes() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", "BRuno");

        Collection dataSource = countryRepository.findAll();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSource);

        String path = ResourceUtils.getFile("classpath:" + TEMPLATE).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
        return exportJasperReportBytes(jasperPrint);
    }

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        return outputStream.toByteArray();
    }
}
