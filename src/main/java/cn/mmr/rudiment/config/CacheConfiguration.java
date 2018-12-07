package cn.mmr.rudiment.config;

import com.google.common.collect.ImmutableMap;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

  /**
   * time to live.
   */
  @Value("${note.typer.ttl:20}")
  private long ttl;

  public static final String CACHE_MANAGER = "cacheManager";

  public static final String CONTACT_CACHE = "contactCache";

  @Bean(CACHE_MANAGER)
  public CacheManager config(RedisConnectionFactory connectionFactory) {
    RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
    RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
    defaultCacheConfig = defaultCacheConfig
        .serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()));
    defaultCacheConfig = defaultCacheConfig.serializeValuesWith(SerializationPair
        .fromSerializer(new GenericJackson2JsonRedisSerializer()));
    RedisCacheManager cacheManager =
        new RedisCacheManager(
            redisCacheWriter,
            RedisCacheConfiguration.defaultCacheConfig(),
            ImmutableMap.of(CONTACT_CACHE, defaultCacheConfig.entryTtl(Duration.ofSeconds(ttl))));
    return cacheManager;
  }
}
