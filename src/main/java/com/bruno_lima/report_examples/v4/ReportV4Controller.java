package com.bruno_lima.report_examples.v4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v4")
public class ReportV4Controller {

    @Autowired
    private ReportV4Service reportService;

    @GetMapping("/report")
    public ResponseEntity<Resource> report() throws Exception {
        byte[] bytes = reportService.generateReportInBytes();

        if (null == bytes) {
            throw new Exception("File Download Failed");
        }

        ByteArrayResource resource = new ByteArrayResource(bytes);
        String fileName = "Report.pdf";

        return ResponseEntity.ok()
                .header("CONTENT_DISPOSITION",   "inline; filename=\"" + fileName + "\"")
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
