package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Comment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Comment entity.
 */
public interface CommentSearchRepository extends ElasticsearchRepository<Comment, Long> {
}
