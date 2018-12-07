package cn.mmr.rudiment.controller;

import cn.mmr.rudiment.AbstractTest;
import cn.mmr.rudiment.config.AmqpConfig;
import cn.mmr.rudiment.listener.RabbitmqListenerTest;
import cn.mmr.rudiment.model.ContactPerson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactPersonAmqpTest extends AbstractTest {

  @Autowired
  RabbitTemplate rabbitTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private RabbitmqListenerTest rabbitmqListenerTest;

  @Test(timeout = 5000)
  public void testAmqpListener() throws InterruptedException, JsonProcessingException {
    rabbitmqListenerTest.initMessageData();
    ContactPerson contactPerson =
        ContactPerson.builder()
            .contactPerson("张三")
            .contactPhone("13289897676")
            .remark("Beijing")
            .build();
    rabbitTemplate.convertAndSend(AmqpConfig.RECEIPT_EXCHANGE, AmqpConfig.RECEIPT_VOUCHER_QUEUE,
        objectMapper.writeValueAsString(contactPerson));
    periodicCheck(new PassableRunnable() {

      boolean passed = false;

      @Override
      public boolean isPassed() {
        return passed;
      }

      @Override
      public void run() {
        List<ContactPerson> contactPersonList = rabbitmqListenerTest.getMessageDatas();
        if (CollectionUtils.isNotEmpty(contactPersonList)) {
          Assertions.assertThat(contactPersonList.get(0))
              .matches(contactPerson -> Objects.equals(contactPerson.getContactPerson(), "张三"),
                  "联系人姓名不匹配 ");
          passed = true;
        }
      }
    });
  }

}
