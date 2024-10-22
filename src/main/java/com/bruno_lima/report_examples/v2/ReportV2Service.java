package com.bruno_lima.report_examples.v2;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class    ReportV2Service {
    private final String TEMPLATE = "reports/reportV2.jrxml";

    public byte[] generateReportInBytes() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", "BRuno");

        Collection dataSourceData = getDataSourceStaticData();
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSourceData);

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

    private Collection getDataSourceStaticData() {
        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> item1 = new HashMap<>();
        item1.put("id", 1);
        item1.put("name", "Eclipse");
        items.add(item1);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 2);
        item2.put("name", "Lyset");
        items.add(item2);

        return items;
    }
}
