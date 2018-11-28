package cn.mmr.rudiment.controller;

import cn.mmr.rudiment.mapper.ContactPersonMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/mmr")
public class ContactPersonController {

  @Autowired
  private ContactPersonMapper contactPersonMapper;

  @GetMapping("/list/{personName}")
  public ResponseEntity<?> autoDistributeSetList(@PathVariable("personName") String personName) {
    return ResponseEntity.ok(contactPersonMapper.getContactPersonIdsByName(personName));
  }

}
