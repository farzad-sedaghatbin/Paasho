package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Titles;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Titles entity.
 */
public interface TitlesSearchRepository extends ElasticsearchRepository<Titles, Long> {
}
