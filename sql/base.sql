
CREATE TABLE `T_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(22) DEFAULT NULL,

  `create_by` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(45) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '修改人',
  `update_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `uuid` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表';
