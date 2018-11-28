package cn.mmr.rudiment.mapper;

import cn.mmr.rudiment.model.ContactPerson;
import java.util.List;

public interface ContactPersonMapper {

  public Long saveContactPerson(ContactPerson contactPerson);

  public ContactPerson findContactById(long id);

  public void deleteAll();

  public void insertContactsPersonBatch(List<ContactPerson> contactPersonList);

  List<ContactPerson> getContactPersonIdsByName(String customerName);
}
