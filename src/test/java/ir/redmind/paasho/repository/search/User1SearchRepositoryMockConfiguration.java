package ir.redmind.paasho.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of User1SearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class User1SearchRepositoryMockConfiguration {

    @MockBean
    private User1SearchRepository mockUser1SearchRepository;

}
