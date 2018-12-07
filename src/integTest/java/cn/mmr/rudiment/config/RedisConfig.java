package cn.mmr.rudiment.config;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.embedded.RedisServer;

@Configuration
@Profile("integTest")
public class RedisConfig {
  private RedisServer redisServer;

  @Value("localhost")
  private String redisHost;

  @Value("${random.int[58000,60000]}")
  private int redisPort;

  @Bean
  @Primary
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory(
        new RedisStandaloneConfiguration(redisHost, redisPort)
    );
    return factory;
  }

  @PostConstruct
  public void startRedis() throws IOException {
    redisServer = new RedisServer(redisPort);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    redisServer.stop();
  }
}
