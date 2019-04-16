package ir.redmind.paasho.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(ir.redmind.paasho.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".participants", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".medias", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".contacts", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".rates", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".favorits", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Contact.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Category.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Category.class.getName() + ".ids", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Notification.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Notification.class.getName() + ".notifications", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Setting.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Factor.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Media.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Rating.class.getName(), jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Category.class.getName() + ".categories", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".rates", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Event.class.getName() + ".factors", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".factors", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Notification.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.User1.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Category.class.getName() + ".events", jcacheConfiguration);
            cm.createCache(ir.redmind.paasho.domain.Category.class.getName() + ".users", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
