package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.User1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the User1 entity.
 */
public interface User1SearchRepository extends ElasticsearchRepository<User1, Long> {
}
