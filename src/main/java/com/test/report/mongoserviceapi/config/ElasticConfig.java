package com.test.report.mongoserviceapi.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.test.report.mongoserviceapi.repositories", elasticsearchTemplateRef = "elasticsearchTemplate")
@ComponentScan(basePackages = "com.test.report.mongoserviceapi.service")
public class ElasticConfig {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;
    @Value("${elasticsearch.username}")
    private String username;
    @Value("${elasticsearch.password}")
    private String password;
    @Value("${timeout}")
    private int timeout;
    @Value("${elasticsearch.maxRetryTimeout:1800000}")
    private int maxRetryTimeout;
    private final int timeOut = 30;
    private final int sockTimeOut = 1000;

    @Bean(name = "elasticsearchTemplate")
    public ElasticsearchOperations elasticsearchOperations() {
        return new ElasticsearchRestTemplate(getHighLevelClient());
    }

    @Bean(name = "HighLevelClient")
    public RestHighLevelClient getHighLevelClient() {
        CredentialsProvider
                credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder
                builder = RestClient.builder(new HttpHost(this.host, port, HttpHost.DEFAULT_SCHEME_NAME))
                .setHttpClientConfigCallback(httpClientBuilder ->
                                                     httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider).disableAuthCaching());
        builder.setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectTimeout(timeOut * sockTimeOut)
                .setSocketTimeout(timeOut * sockTimeOut)
                .setConnectionRequestTimeout(0));
        RestClient client = builder.build();
        return new RestHighLevelClientBuilder(client)
                .setApiCompatibilityMode(true)
                .build();
    }

    @Bean(destroyMethod = "close")
    public RestClient restClient() {
        return getHighLevelClient().getLowLevelClient();
    }
}
