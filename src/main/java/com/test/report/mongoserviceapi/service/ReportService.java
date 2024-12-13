package com.test.report.mongoserviceapi.service;

import com.test.report.mongoserviceapi.model.ReportCollection;
import com.test.report.mongoserviceapi.respository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {


    @Autowired
    private ReportRepository reportRepository;
/*
    private final List<ReportCollection> reportCollectionList;
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
        this.reportCollectionList = initLoadData();

    }

    private List<ReportCollection> initLoadData() {
        List<ReportCollection> reportCollectionList = reportRepository.findAll();
        return reportCollectionList;
    }

    public ReportCollection getSingleReportDoc(String reportID) {
        return reportRepository.findByReportId(reportID).orElseThrow(() -> new RuntimeException(
                String.format("Cannot Find Report by ID %s", reportID)));
    }

    public ReportCollection getReportById(String rptId) {
        Optional<ReportCollection> optional = reportCollectionList
                        .stream()
                        .filter(report -> report.getReportId().equalsIgnoreCase(rptId))
                        .findFirst();
        return optional.orElseThrow(() -> new RuntimeException(
                String.format("Could not Found Report with ID %s",rptId
                )));
    }
*/

/*    public String saveReportData(ReportCollection reportCollection) {
        final ReportCollection collection = reportRepository.save(reportCollection);
        return String.format("report: %s saved",collection.getReportId());
    }

    public ReportCollection getReportData(String rptId) {
        Optional<ReportCollection> collectionOptional = reportRepository.findByReportId(rptId);
        return collectionOptional.orElseThrow(
                ()-> new RuntimeException(
                        String.format("Couldn't found report with ID: %s",rptId)
                ));
    }*/
}
