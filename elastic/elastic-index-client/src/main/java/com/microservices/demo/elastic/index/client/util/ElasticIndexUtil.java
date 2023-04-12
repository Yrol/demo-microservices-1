package com.microservices.demo.elastic.index.client.util;

import com.microservices.demo.elastic.model.index.IndexModel;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class will convert twitter index model objects to a list of queries (to be able to save them to elastic search)
 * Extends the IndexModel interface (using generic type T)
 * */
@Component
public class ElasticIndexUtil<T extends IndexModel> {

    public List<IndexQuery> getIndexQueries(List<T> documents) {
        return documents.stream()
                .map(document -> new IndexQueryBuilder()
                        .withId(document.geId())
                        .withObject(document)
                        .build()
                ).collect(Collectors.toList());
    }
}
