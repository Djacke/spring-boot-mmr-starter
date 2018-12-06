package cn.mmr.rudiment.controller;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.mmr.rudiment.mapper.ContactPersonMapper;
import cn.mmr.rudiment.model.ContactPerson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.mongodb.core.MongoTemplate;
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

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private MongoTemplate mongoTemplate;

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
    mongoTemplate.dropCollection(ContactPerson.class);
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

  @Test
  public void testSaveContactPerson() throws Exception {
    ContactPerson contactPerson =
        ContactPerson.builder()
            .id(100L)
            .contactPerson("ceshi1")
            .contactPhone("13500110022")
            .address("北京市朝阳区")
            .remark("ceshi")
            .build();
    mockMvc
        .perform(
            post("/v1/api/mmr/mongo")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(contactPerson)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(100))
        .andExpect(jsonPath("$.contactPerson").value("ceshi1"));
  }

  @Test
  public void testFindConstactPersonByName() throws Exception {
    ContactPerson contactPerson =
        ContactPerson.builder()
            .id(100L)
            .contactPerson("ceshi1")
            .contactPhone("13500110022")
            .address("北京市朝阳区")
            .remark("ceshi")
            .build();
    mongoTemplate.insert(contactPerson);
    mockMvc
        .perform(get("/v1/api/mmr/mongo/{name}", "ceshi"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$.[0].contactPerson").value("ceshi1"));
  }

  @Test
  public void testFindByContactPersonLike() throws Exception {
    ContactPerson contactPerson =
        ContactPerson.builder()
            .id(100L)
            .contactPerson("ceshi1")
            .contactPhone("13500110022")
            .address("北京市朝阳区")
            .remark("ceshi")
            .build();
    mongoTemplate.insert(contactPerson);
    mockMvc
        .perform(get("/restdata/contact/search/findByContactPersonLike?contactPerson=ceshi"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$._embedded.contact", hasSize(1)))
        .andExpect(jsonPath("$._embedded.contact.[0].contactPerson").value("ceshi1"));
  }
}
