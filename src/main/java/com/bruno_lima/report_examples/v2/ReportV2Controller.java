package com.bruno_lima.report_examples.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class ReportV2Controller {

    @Autowired
    private ReportV2Service reportService;

    @GetMapping("/report")
    public ResponseEntity<Resource> report() throws Exception {
        byte[] bytes = this.reportService.generateReportInBytes();

        if (null == bytes) {
            throw new Exception("File Download Failed");
        }

        boolean isForceDownload = false;
        String disposition = isForceDownload ? "attachment" : "inline";
        MediaType mediaType = isForceDownload ? MediaType.APPLICATION_OCTET_STREAM : MediaType.APPLICATION_PDF;

        ByteArrayResource resource = new ByteArrayResource(bytes);
        String fileName = "Report.pdf";

        return ResponseEntity.ok()
                .header("CONTENT_DISPOSITION",
                        disposition + "; filename=\"" + fileName + "\""
                )
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }
}
