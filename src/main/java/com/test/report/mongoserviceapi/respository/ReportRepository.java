package com.test.report.mongoserviceapi.respository;

import com.test.report.mongoserviceapi.model.ReportCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<ReportCollection, String> {
    Optional<ReportCollection> findByReportId(String reportID);
}
