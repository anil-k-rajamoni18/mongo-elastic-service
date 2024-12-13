package com.test.report.mongoserviceapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

import static com.test.report.mongoserviceapi.constant.AppConstants.INDEX_NAME;

@AllArgsConstructor
@Data
@Document(indexName = INDEX_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticDocument {

    @Id
    private int sequenceNumber;
    private String productID;
    private String productName;
    private String reportID;
    private String reportName;
    private String reportType;
    private String reportPropVal;
    private List<Subreports> subreports;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Subreports {
        private String subreportID;
        private String description;
        private String productName;
        private String reportName;
        private String subreportName;
        private String subreportPropVal;
        private String subreportPropCD;
    }
}
