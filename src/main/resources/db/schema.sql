drop table customer_contact_person if exists;
CREATE TABLE `customer_contact_person` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact_person` varchar(100) NOT NULL COMMENT '联系人',
  `contact_phone` varchar(11) NOT NULL COMMENT '联系电话',
  `source` int(11) NOT NULL COMMENT '电话来源（crm，agent，app 等）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_legal_person` int(4) NOT NULL DEFAULT '0' COMMENT '是否为法人',
  `is_sign_person` int(4) NOT NULL DEFAULT '0' COMMENT '是否是签约人',
  `is_main_contact` int(4) NOT NULL DEFAULT '0' COMMENT '是否为主联系人',
  `duty` varchar(20) NOT NULL DEFAULT '""' COMMENT '职务',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `address` varchar(200) NOT NULL DEFAULT 'EMPTY STRING' COMMENT '联系人地址',
  `create_user_id` bigint(20) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;
