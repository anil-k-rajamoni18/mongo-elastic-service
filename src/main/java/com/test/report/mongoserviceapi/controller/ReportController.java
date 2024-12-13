package com.test.report.mongoserviceapi.controller;

import com.test.report.mongoserviceapi.config.ReportProp;
import com.test.report.mongoserviceapi.model.ReportCollection;
import com.test.report.mongoserviceapi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rpt")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportProp reportProp;

    @GetMapping("/status")
    public String applicationStatus() {
        return "mongo rpt api is UP & RUNNING v1.0";
    }

    @GetMapping("/{reportID}")
    public ResponseEntity<ReportCollection> getReportByID(@PathVariable String reportID) {
        return ResponseEntity.ok()
                .body(reportProp.getReportById(reportID));
    }

    @GetMapping("/{reportID}/{subReportID}")
    public ResponseEntity<ReportCollection.Subreports> getSubReportID(@PathVariable String reportID, @PathVariable String subReportID) {
        return ResponseEntity.ok()
                .body(reportProp.getSubReportById(reportID, subReportID));
    }
/*
    @PostMapping("/saveRpt")
    public String saveReportData(@RequestBody ReportCollection reportCollection) {
        return reportService.saveReportData(reportCollection);
    }

    @GetMapping("/{rptID}")
    public ReportCollection getReportById(String rptID){
        return reportService.getReportData(rptID);
    }*/

}
