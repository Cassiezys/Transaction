CREATE TABLE `user` (
`id`  bigint(100) NOT NULL ,
`account_id`  varchar(100) NULL ,
`name`  varchar(100) NULL ,
`token`  char(36) NULL COMMENT 'oAuth' ,
`gmt_create`  bigint(20) NULL ,
`gmt_modified`  bigint(20) NULL ,
`bio`  varchar(256) NULL COMMENT '简介' ,
`avatar_url`  varchar(100) NULL COMMENT '图片地址' ,
PRIMARY KEY (`id`)
)
;

