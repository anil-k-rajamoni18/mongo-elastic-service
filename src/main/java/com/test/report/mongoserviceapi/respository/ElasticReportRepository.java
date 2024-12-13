package com.test.report.mongoserviceapi.respository;

import com.test.report.mongoserviceapi.model.ElasticDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticReportRepository extends ElasticsearchRepository<ElasticDocument, String> {
    ElasticDocument findByProductID(String productId);
}
