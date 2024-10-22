package com.bruno_lima.report_examples.v5;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.util.*;

@Service
public class ReportV5Service {
    private final String TEMPLATE = "reports/reportV5.jrxml";

    public byte[] generateReportInBytes(ReportTypeEnum fileType) throws Exception {
        Collection dataSourceData = getDataSourceStaticData();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("author", "BRuno");

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(dataSourceData);
        String path = ResourceUtils.getFile("classpath:" + TEMPLATE).getAbsolutePath();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);
        return exportJasperReportBytes(jasperPrint, fileType);
    }

    public byte[] exportJasperReportBytes(JasperPrint jasperPrint, ReportTypeEnum reportType) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        switch (reportType) {
            case CSV:
                // Export to CSV
                JRCsvExporter csvExporter = new JRCsvExporter();
                csvExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                csvExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                csvExporter.exportReport();
                break;
            case XLSX:
                // Export to XLSX
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.exportReport();
                break;
            case HTML:
                // Export to HTML
                HtmlExporter htmlExporter = new HtmlExporter();
                htmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                htmlExporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
                htmlExporter.exportReport();
                break;
            case XML:
                // Export to XML
                JRXmlExporter xmlExporter = new JRXmlExporter();
                xmlExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xmlExporter.setExporterOutput(new SimpleXmlExporterOutput(outputStream));
                xmlExporter.exportReport();
                break;
            case DOC:
                // Export to DOCX (RTF format)
                JRRtfExporter docxExporter = new JRRtfExporter();
                docxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                docxExporter.setExporterOutput(new SimpleWriterExporterOutput(outputStream));
                docxExporter.exportReport();
                break;
            default:
                // Export to PDF by default
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
                break;
        }
        return outputStream.toByteArray();
    }

    public MediaType getMediaType(ReportTypeEnum reportType) {
        return getMediaType(reportType, false);
    }

    public MediaType getMediaType(ReportTypeEnum reportType, boolean isForceDownload) {
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        switch (reportType) {
            case PDF:
                mediaType = isForceDownload ? MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_PDF;
                break;

            case HTML:
                mediaType = isForceDownload ? MediaType.APPLICATION_OCTET_STREAM : MediaType.TEXT_HTML;
                break;

            case XML:
                mediaType = isForceDownload ? MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_XML;
                break;
        }

        return mediaType;
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
