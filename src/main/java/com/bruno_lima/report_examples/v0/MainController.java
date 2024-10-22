package com.bruno_lima.report_examples.v0;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @GetMapping
    public String index() {
        List<String> list = new ArrayList<>();
        list.add("/v1/report");
        list.add("/v2/report");
        list.add("/v3/report");
        list.add("/v4/report");
        list.add("/v5/report");

        StringBuilder html = new StringBuilder("Reports");
        html.append("<ul>");

        html.append(list.stream().map((String endpoint) -> "<li> <a href='" + endpoint + "'>" + endpoint + "</a>")
                .collect(Collectors.joining()));

        html.append("</ul>");

        return html.toString();
    }
}
