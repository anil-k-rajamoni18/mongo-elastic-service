package com.test.report.mongoserviceapi.service;

import com.test.report.mongoserviceapi.constant.AppConstants;
import com.test.report.mongoserviceapi.dtos.ElasticRequest;
import com.test.report.mongoserviceapi.model.ElasticDocument;
import com.test.report.mongoserviceapi.respository.ElasticReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ElasticService {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private ElasticReportRepository elasticReportRepository;
    public ElasticDocument findProductById(String prodId) {
        ElasticDocument elasticDocument =
                elasticReportRepository.findByProductID(prodId);
        log.info("elastic document received {}", elasticDocument);
        return elasticDocument;
    }


    public List<ElasticDocument> findReportByName(ElasticRequest elasticRequest) {
        MultiMatchQueryBuilder multiMatchQueryBuilder =
                QueryBuilders.multiMatchQuery(elasticRequest.reportName(), "reportName")
                        .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX);

        Query query = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQueryBuilder)
                .withPageable(PageRequest.of(0, 50))
                .build();

        SearchHits<ElasticDocument> searchHits =
                elasticsearchOperations.search(
                        query,
                        ElasticDocument.class,
                        IndexCoordinates.of(AppConstants.INDEX_NAME)
                );

        return searchHits.getSearchHits()
                .stream()
                .map(document -> document.getContent())
                .collect(Collectors.toList());
    }

    public List<ElasticDocument> getReportNames(ElasticRequest elasticRequest) {
        Criteria criteria = new Criteria(AppConstants.PRODUCT_NAME_FIELD).is(elasticRequest.productName());
        Query searchQuery = new CriteriaQuery(criteria);

        searchQuery.setPageable(PageRequest.of(0, 100));
        SearchHits<ElasticDocument> reportDataSearchHits = elasticsearchOperations.search(
                searchQuery,
                ElasticDocument.class,
                IndexCoordinates.of(AppConstants.INDEX_NAME)
        );
        return reportDataSearchHits.getSearchHits().stream().map(value -> value.getContent()).collect(Collectors.toList());
    }

    public List<ElasticDocument> getBySubReportName(ElasticRequest request) {
        log.info(request.subReportName());
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.multiMatchQuery(request.subReportName(), "subreports.subreportName")
                                .type(MultiMatchQueryBuilder.Type.BOOL_PREFIX))
                .must(QueryBuilders.termsQuery("reportID.keyword", "RPT1"));

        log.info(String.valueOf(boolQuery));

        Query searchQuery = new StringQuery(String.valueOf(boolQuery));
        SearchHits<ElasticDocument> searchHits = elasticsearchOperations.search(
                searchQuery,
                ElasticDocument.class,
                IndexCoordinates.of(AppConstants.INDEX_NAME)
        );

        return searchHits.getSearchHits()
                .stream()
                .map(value -> value.getContent())
                .collect(Collectors.toList());

    }
}
