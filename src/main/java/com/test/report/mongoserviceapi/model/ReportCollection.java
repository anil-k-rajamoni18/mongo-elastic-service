package com.test.report.mongoserviceapi.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "testLookUpColl")
public class ReportCollection {
    @Id
    private ObjectId id;
    private Integer sequenceNumber;
    private String productId;
    private String productName;
    private String reportId;
    private String reportName;
    private String reportType;
    private String reportPropVal;
    private String dataSegment;
    private String productCategory;
    private String effectiveDtime;
    private String expireDtime;
    private String createdDtime;
    private String modifiedDtime;
    private String modifiedBy;
    private List<Subreports> subreports;

    @Data
    public static class Subreports {
        private String subreportId;
        private String subreportName;
        private String subreportPropVal;
        private String docID;
        private String commentText;
        private String rptIndexName;
        private String description;
    }
}
