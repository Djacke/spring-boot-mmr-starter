package cn.mmr.rudiment.repository;

import cn.mmr.rudiment.model.ContactPerson;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "contact", path = "contact", exported = true)
public interface ContactPersonRepository extends MongoRepository<ContactPerson, Long> {

  public ContactPerson save(ContactPerson contactPerson);

  public List<ContactPerson> findByContactPersonLike(@Param("contactPerson") String contactPerson);

}
