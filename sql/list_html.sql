(
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`url`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`html`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`crawledTime`  datetime NULL DEFAULT NULL ,
`urlMd5`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pageMd5`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`history`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`extracted`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
INDEX `pagemd5` (`pageMd5`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25
ROW_FORMAT=COMPACT
;