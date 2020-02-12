CREATE TABLE `NewTable` (
`id`  bigint(20) NULL AUTO_INCREMENT ,
`notifier`  bigint(20) NULL COMMENT '创建通知者' ,
`receiver`  bigint(20) NULL COMMENT '接收通知者' ,
`outerid`  bigint(20) NULL COMMENT '商品的id' ,
`type`  int(11) NULL COMMENT '评论还是回复' ,
`gmt_create`  bigint(20) NULL ,
`status`  int(11) NULL DEFAULT 0 COMMENT '未读为（0）' ,
`notifier_name`  varchar(100) NULL COMMENT '创建通知的用户名' ,
`outer_title`  varchar(256) NULL COMMENT '商品的标题' ,
PRIMARY KEY (`id`)
)
;



