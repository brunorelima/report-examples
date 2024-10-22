package com.bruno_lima.report_examples.v5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/v5")
public class ReportV5Controller {

    @Autowired
    private ReportV5Service reportService;

    @GetMapping("/report")
    public ResponseEntity<Resource> report(
            @RequestParam(value = "fileType", defaultValue = "pdf") String fileType,
            @RequestParam(value = "download", defaultValue = "false") Boolean isForceDownload
            ) throws Exception {
        ReportTypeEnum reportType = ReportTypeEnum.getReportTypeEnum(fileType);

        byte[] bytes = reportService.generateReportInBytes(reportType);

        if (null == bytes) {
            throw new Exception("File Download Failed");
        }

        String disposition = isForceDownload ? "attachment" : "inline";
        MediaType mediaType = reportService.getMediaType(reportType, isForceDownload);

        ByteArrayResource resource = new ByteArrayResource(bytes);
        String fileName = "Report" + "_" + LocalDateTime.now() + "." + fileType ;

        return ResponseEntity.ok()
                .header("CONTENT_DISPOSITION",
                        disposition + "; filename=\"" + fileName + "\""
                )
                .contentLength(resource.contentLength())
                .contentType(mediaType)
                .body(resource);
    }
}
