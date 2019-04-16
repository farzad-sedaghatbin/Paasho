package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Rating;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Rating entity.
 */
public interface RatingSearchRepository extends ElasticsearchRepository<Rating, Long> {
}
