package cn.mmr.rudiment.controller;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.mmr.rudiment.mapper.ContactPersonMapper;
import cn.mmr.rudiment.model.ContactPerson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ActiveProfiles("integTest")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactPersonControllerTest {

  @Autowired
  protected JdbcTemplate jdbcTemplate;

  @Autowired
  ContactPersonMapper contactPersonMapper;

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * 执行之前.
   */
  @Before
  public void setUp() {
    contactPersonMapper.deleteAll();
    jdbcTemplate.execute(
        "delete  from `customer_contact_person`");
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  /**
   * 执行后.
   */
  @After
  public void clear() {
    jdbcTemplate.execute(
        "delete  from `customer_contact_person`");
  }

  @Test
  public void testContactPerson() throws Exception {
    ContactPerson contactPerson =
        ContactPerson.builder()
            .contactPerson("ceshi1")
            .contactPhone("13500110022")
            .address("北京市朝阳区")
            .remark("ceshi")
            .build();
    contactPersonMapper.saveContactPerson(contactPerson);
    mockMvc
        .perform(
            get("/v1/api/mmr/list/{personName}", "ceshi1")
                .accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }
}
