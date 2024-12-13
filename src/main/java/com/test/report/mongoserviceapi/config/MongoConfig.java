package com.test.report.mongoserviceapi.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@SuppressWarnings("unused")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.application.name}")
    private String consumerAppName;

    @Bean
    public MongoClient mongoClient() {
        final MongoClientSettings mongoClientSettings = getMongoClientSettings(mongoProps());
        //ConnectionString connectionString = new ConnectionString("mongodb://reports_hub_admin_user:reports_hub_admin_user_123@rp000094224.uhc.com:27017,rp000094225.uhc.com:27017,rp000094226.uhc.com:27017/reports_hub_dev?replicaSet=rd0");
        //final MongoClientSettings mongoClientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    private MongoClientSettings getMongoClientSettings(final MongoProps mongoProps) {
        return MongoClientSettings.builder()
                .readPreference(mongoProps.getReadPreference())
                .applicationName(consumerAppName)
                .credential(
                        MongoCredential.createCredential(
                                mongoProps.getUsername(),
                                mongoProps.getAuthDbName(),
                                mongoProps.getPassword().toCharArray())
                )
                .applyToConnectionPoolSettings(
                        builder ->
                                builder.maxSize(mongoProps.getMaxPoolSize())
                                        .minSize(mongoProps.getMinPoolSize())
                )
                .applyToClusterSettings(
                        builder ->
                                builder.hosts(mongoProps.getHosts())
                                        .requiredReplicaSetName(mongoProps.getReplicaSet())
                )
                .applyToSocketSettings(
                        builder ->
                                builder.connectTimeout(mongoProps.getConnectionTimeoutSec(),
                                                       TimeUnit.SECONDS)
                )
                .build();
    }

    @ConfigurationProperties(prefix = "database.mongodb.reports-hub")
    @Bean
    public MongoProps mongoProps() {
        return new MongoProps();
    }

    @Override
    protected String getDatabaseName() {
        return mongoProps().authDbName;
    }

    @Getter
    @Setter
    class MongoProps {

        private static final Integer MAX_POOL_SIZE = 100;
        private static final Integer MIN_POOL_SIZE = 100;

        private String dbName;
        private String username;
        private String password;
        private String authDbName;
        private List<ServerAddress> hosts;
        private String replicaSet;
        private Integer connectionTimeoutSec;
        private ReadPreference readPreference;
        private Integer maxPoolSize;
        private Integer minPoolSize;

        // default values
        MongoProps() {
            this.readPreference = ReadPreference.primary();
            this.maxPoolSize = MAX_POOL_SIZE;
            this.minPoolSize = MIN_POOL_SIZE;
        }

        public void setHosts(final List<String> hosts) {
            this.hosts = hosts.stream()
                    .map(host -> new ServerAddress(host, ServerAddress.defaultPort()))
                    .toList();
        }

        public void setReadPreference(final String readPreference) {
            this.readPreference = ReadPreference.valueOf(readPreference);
        }
    }
}
