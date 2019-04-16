package ir.redmind.paasho.repository.search;

import ir.redmind.paasho.domain.Setting;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Setting entity.
 */
public interface SettingSearchRepository extends ElasticsearchRepository<Setting, Long> {
}
