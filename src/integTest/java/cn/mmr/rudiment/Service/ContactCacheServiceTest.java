package cn.mmr.rudiment.Service;

import cn.mmr.rudiment.model.ContactPerson;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactCacheServiceTest {

  @Autowired
  private ContactCacheService contactCacheService;

  @Test
  public void testCacheContactPerson() {
    ContactPerson contactPerson =
        ContactPerson.builder()
            .contactPhone("13245678967")
            .contactPerson("席梦思")
            .remark("remark")
            .address("BeijingChaoyang")
            .build();
    contactCacheService.refreshContactPerson(contactPerson);
    ContactPerson contactPerson1 = contactCacheService.getContactPerson("席梦思");
    Assertions.assertThat(contactPerson1)
        .matches(c -> Objects.equals(c.getContactPhone(), "13245678967"), "联系人电话不匹配");
  }
}
