package cn.mmr.rudiment.controller;

import cn.mmr.rudiment.mapper.ContactPersonMapper;
import cn.mmr.rudiment.model.ContactPerson;
import cn.mmr.rudiment.repository.ContactPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/mmr")
public class ContactPersonController {

  @Autowired
  private ContactPersonMapper contactPersonMapper;

  @Autowired
  private ContactPersonRepository contactPersonRepository;

  @GetMapping("/list/{personName}")
  public ResponseEntity<?> queryContactPersons(@PathVariable("personName") String personName) {
    return ResponseEntity.ok(contactPersonMapper.getContactPersonIdsByName(personName));
  }

  @PostMapping("/mongo")
  public ResponseEntity<?> saveContactPerson(@RequestBody ContactPerson contactPerson) {
    return ResponseEntity.ok(contactPersonRepository.save(contactPerson));
  }

  @GetMapping("/mongo/{name}")
  public ResponseEntity<?> getContactPerson(@PathVariable String name) {
    return ResponseEntity.ok(contactPersonRepository.findByContactPersonLike(name));
  }

}
