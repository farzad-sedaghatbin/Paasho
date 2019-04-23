package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Event;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Event entity.
 */
public interface EventSearchRepository extends ElasticsearchRepository<Event, Long> {
    Event findByCode(String code);
}
