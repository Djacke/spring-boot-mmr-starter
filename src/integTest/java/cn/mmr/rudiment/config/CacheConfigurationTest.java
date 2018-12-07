package cn.mmr.rudiment.config;

import java.util.Arrays;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("integTest")
public class CacheConfigurationTest {

  @Bean(CacheConfiguration.CACHE_MANAGER)
  @Primary
  public CacheManager noteTyperCacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache(CacheConfiguration.CONTACT_CACHE)));
    return cacheManager;
  }
}
