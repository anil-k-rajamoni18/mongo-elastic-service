package com.test.report.mongoserviceapi.config;

import com.mongodb.client.MongoClient;
import com.test.report.mongoserviceapi.model.ReportCollection;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReportProp {


    private final MongoOperations mongoOperations;
    private final List<ReportCollection> reportCollectionList;

    public ReportProp(MongoOperations mongoOperations, MongoClient mongoClient) {
        this.mongoOperations = mongoOperations;
        this.reportCollectionList = this.mongoOperations.findAll(ReportCollection.class,"testLookUpColl");
    }

    public ReportCollection getReportById(String rptId) {
        Optional<ReportCollection>
                optional = reportCollectionList
                .stream()
                .filter(report -> report.getReportId().equalsIgnoreCase(rptId))
                .findFirst();
        return optional.orElseThrow(() -> new RuntimeException(
                String.format("Could not Found Report with ID %s",rptId
                )));
    }

    public ReportCollection.Subreports getSubReportById(String rptID, String subRptId) {
        Optional<ReportCollection> reportCollection = reportCollectionList
                .stream()
                .filter(report -> report.getReportId().equalsIgnoreCase(rptID))
                .findFirst();
        return reportCollection.orElseThrow(() -> new RuntimeException(
                String.format("Could not Found Report with ID %s", rptID)))
                .getSubreports().stream()
                .filter(subreports -> subreports.getSubreportId().equalsIgnoreCase(subRptId))
                .findFirst().orElseThrow(
                        () -> new RuntimeException(
                                String.format("Could not Found Report with ID %s",subRptId))
                );
    }
}
