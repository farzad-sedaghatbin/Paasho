package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Media;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Media entity.
 */
public interface MediaSearchRepository extends ElasticsearchRepository<Media, Long> {
}
