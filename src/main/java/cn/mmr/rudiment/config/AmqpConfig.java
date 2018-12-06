package cn.mmr.rudiment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class AmqpConfig implements RabbitListenerConfigurer {

  public static final String RECEIPT_EXCHANGE = "receipt.voucher";
  public static final String RECEIPT_VOUCHER_QUEUE = "receipt.voucher.in";

  /**
   * 消息发送转换配置.
   */
  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter(ObjectMapper objectMapper) {
    return new Jackson2JsonMessageConverter(objectMapper);
  }

  /**
   * 消息消费转换配置.
   */
  @Bean
  public MappingJackson2MessageConverter jackson2Converter() {
    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    return converter;
  }

  /**
   * set message converter.
   */
  @Bean
  public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setMessageConverter(jackson2Converter());
    return factory;
  }

  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
  }

  @Bean
  public TopicExchange receiptExchange() {
    return (TopicExchange) ExchangeBuilder.topicExchange(RECEIPT_EXCHANGE).durable(true).build();
  }

  @Bean
  public Queue receiptVoucherQueue() {
    return QueueBuilder.durable(RECEIPT_VOUCHER_QUEUE).build();
  }

  /**
   * binding queue to exchange.
   */
  @Bean
  public Binding receiptVoucherBind(TopicExchange receiptExchange, Queue receiptVoucherQueue) {
    return BindingBuilder.bind(receiptVoucherQueue).to(receiptExchange).with(RECEIPT_VOUCHER_QUEUE);
  }
}
