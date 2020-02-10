CREATE TABLE comment (
`id`  bigint(20) NOT NULL AUTO_INCREMENT ,
`parent_id`  bigint(20) NOT NULL COMMENT ''商品的id'' ,
`type`  int(11) NULL COMMENT ''1：一级评论；2：二级评论'' ,
`commentator`  bigint(20) NULL COMMENT ''评论者的id'' ,
`gmt_create`  bigint(20) NULL ,
`gmt_modified`  bigint(20) NULL ,
`like_count`  bigint(20) NULL ,
`content`  varchar(1024) NULL ,
`comment_count`  int(11) NULL ,
PRIMARY KEY (`id`)
)
;

