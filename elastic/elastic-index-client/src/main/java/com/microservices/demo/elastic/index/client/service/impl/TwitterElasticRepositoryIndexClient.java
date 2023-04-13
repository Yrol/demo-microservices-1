package com.microservices.demo.elastic.index.client.service.impl;

import com.microservices.demo.elastic.index.client.repository.TwitterElasticSearchIndexRepository;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Making this class as the default implementation of ElasticIndexClient using ConditionalOnProperty.
 * elastic-config.is-repository -> config-server-repository/config-client-kafka_to_elastic.yml.
 * matchIfMissing = true -> making it true by default if the property is missing.
 * **/


@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);

    private final TwitterElasticSearchIndexRepository twitterElasticSearchIndexRepository;

    public TwitterElasticRepositoryIndexClient(TwitterElasticSearchIndexRepository twitterElasticSearchIndexRepository) {
        this.twitterElasticSearchIndexRepository = twitterElasticSearchIndexRepository;
    }

    @Override
    public List<String> save(List<TwitterIndexModel> documents) {
        List<TwitterIndexModel> repositoryResponse = (List<TwitterIndexModel>) twitterElasticSearchIndexRepository.saveAll(documents);
        List<String> ids = repositoryResponse.stream().map(TwitterIndexModel::geId).collect(Collectors.toList());
        LOG.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(), ids);
        return ids;
    }
}
