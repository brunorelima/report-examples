package com.bruno_lima.report_examples.v5;

public enum ReportTypeEnum {
    PDF, CSV, XLSX, HTML, XML, DOC;

    public static ReportTypeEnum getReportTypeEnum(String fileType) {
        switch (fileType.toLowerCase()) {
            case "csv": return ReportTypeEnum.CSV;
            case "xlsx": return ReportTypeEnum.XLSX;
            case "html": return ReportTypeEnum.HTML;
            case "xml": return ReportTypeEnum.XML;
            case "doc": return ReportTypeEnum.DOC;
        };

        return ReportTypeEnum.PDF;
    }
}
