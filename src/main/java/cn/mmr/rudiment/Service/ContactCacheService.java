package cn.mmr.rudiment.Service;

import cn.mmr.rudiment.config.CacheConfiguration;
import cn.mmr.rudiment.model.ContactPerson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ContactCacheService {

  /**
   * 从缓存中获取数据.
   */
  @Cacheable(value = CacheConfiguration.CONTACT_CACHE, key = "#personName")
  public ContactPerson getContactPerson(String personName) {
    return ContactPerson.builder().build();
  }

  /**
   * 更新缓存内容.
   * @param contactPerson
   * @return
   */
  @CachePut(value = CacheConfiguration.CONTACT_CACHE, key = "#contactPerson.contactPerson")
  public ContactPerson refreshContactPerson(ContactPerson contactPerson) {
    logger.debug("更新缓存内容:{}", contactPerson);
    if (contactPerson != null) {
      return contactPerson;
    }
    return ContactPerson.builder().build();
  }

  /**
   * 清除缓存内容.
   * @param personName
   */
  @CacheEvict(cacheNames = CacheConfiguration.CONTACT_CACHE, key = "#personName")
  public void clearContactPerson(String personName) {
    logger.debug("清除已缓存内容:{}", personName);
  }
}
