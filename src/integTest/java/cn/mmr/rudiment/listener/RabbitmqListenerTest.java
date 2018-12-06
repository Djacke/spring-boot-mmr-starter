package cn.mmr.rudiment.listener;

import cn.mmr.rudiment.config.AmqpConfig;
import cn.mmr.rudiment.model.ContactPerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("integTest")
public class RabbitmqListenerTest {

  @Autowired
  ObjectMapper objectMapper;

  private List<ContactPerson> messageDatas = new ArrayList<>();

  public void initMessageData() {
    messageDatas.clear();
  }

  public List<ContactPerson> getMessageDatas() {
    return this.messageDatas;
  }

  @RabbitListener(queues = AmqpConfig.RECEIPT_VOUCHER_QUEUE)
  public void receive(String message) throws IOException {
    System.out.println("接收消息内容:" + message);
    messageDatas.add(objectMapper.readValue(message, ContactPerson.class));
  }
}
