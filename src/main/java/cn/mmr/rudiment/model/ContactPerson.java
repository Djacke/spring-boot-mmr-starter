package cn.mmr.rudiment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contact")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactPerson {

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Long id;

  /**
   * 联系人.
   */
  @Length(max = 100, message = "联系人最多为100个字符")
  private String contactPerson;

  /**
   * 联系电话.
   */
  private String contactPhone;

  /**
   * 电话来源（crm，agent，app 等）.
   */
  private Integer source;

  /**
   * 是否为法人.
   */
  private Integer isLegalPerson;

  /**
   * 是否是签约人.
   */
  private Integer isSignPerson;

  /**
   * 是否主联系人.
   */
  private Integer isMainContact;

  /**
   * 职务.
   */
  private String duty;

  /**
   * 备注.
   */
  @Length(max = 200, message = "备注最多为200个字符")
  private String remark;

  /**
   * 联系人地址.
   */
  private String address;

  private LocalDateTime createTime;
  private LocalDateTime updateTime;
  private Long createUserId;
  private Long updateUserId;

}
