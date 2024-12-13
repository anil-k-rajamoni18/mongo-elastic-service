package com.test.report.mongoserviceapi.controller;

import com.test.report.mongoserviceapi.dtos.ElasticRequest;
import com.test.report.mongoserviceapi.model.ElasticDocument;
import com.test.report.mongoserviceapi.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/elastic")
public class ELasticController {

    @Autowired
    private ElasticService elasticService;

    @GetMapping("/{productId}")
    public ElasticDocument findProductById(@PathVariable  String productId) {
        return elasticService.findProductById(productId);
    }

    @GetMapping
    public List<ElasticDocument> findReportByName(@RequestBody ElasticRequest elasticRequest) {
        return elasticService.findReportByName(elasticRequest);
    }

    @GetMapping("/reportNames")
    public List<ElasticDocument> getReportsByProductId(@RequestBody ElasticRequest elasticRequest) {
        return elasticService.getReportNames(elasticRequest);
    }

    @GetMapping("/sunReportName")
    public List<ElasticDocument> getSubReportByName(@RequestBody ElasticRequest elasticRequest) {
        return elasticService.getBySubReportName(elasticRequest);
    }
}
