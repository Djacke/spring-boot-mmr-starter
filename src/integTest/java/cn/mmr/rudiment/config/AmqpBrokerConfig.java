package cn.mmr.rudiment.config;

import cn.mmr.rudiment.AbstractTest;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ActiveProfiles("integTest")
public class AmqpBrokerConfig {

  private final Broker broker = new Broker();

  /**
   * 新建rabbitmq ConnectionFactory.
   *
   * @param brokerPort port
   */
  @Bean
  public ConnectionFactory connectionFactory(@Value("${random.int[60000,65530]}") int brokerPort)
      throws Exception {
    startBroker(brokerPort);
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory(
        "localhost",
        brokerPort
    );
    connectionFactory.setUsername("guest");
    connectionFactory.setPassword("guest");
    return connectionFactory;
  }

  /**
   * 启动amqp服务.
   *
   * @param brokerPort port
   */
  private void startBroker(int brokerPort) throws Exception {
    System.setProperty("QPID_WORK", "build/qpid-" + RandomStringUtils.random(6, true, true));
    final BrokerOptions brokerOptions = new BrokerOptions();
    brokerOptions.setConfigProperty("qpid.amqp_port", String.valueOf(brokerPort));
    brokerOptions.setConfigProperty("qpid.http_port", String.valueOf(brokerPort + 1));
    brokerOptions.setInitialConfigurationLocation(
        AbstractTest.getResource("amqp/qpid.json").getAbsolutePath());
    broker.startup(brokerOptions);
  }
}
