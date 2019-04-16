package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Factor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Factor entity.
 */
public interface FactorSearchRepository extends ElasticsearchRepository<Factor, Long> {
}
