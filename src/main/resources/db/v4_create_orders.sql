CREATE TABLE `NewTable` (
`id`  bigint(20) NULL AUTO_INCREMENT ,
`creatator`  bigint(20) NULL COMMENT '买家id/订单创建者'
`outerid`  bigint(20) NULL COMMENT '购买的商品的id' ,
`outer_title`  varchar(256) NULL COMMENT '商品名' ,
`receiver`  bigint(20) NULL COMMENT '卖家id' ,
`gmt_create`  bigint(20) NULL ,
`gmt_modified`  bigint(20) NULL ,
`amount`  int(11) NULL COMMENT '购买的数量' ,
`status`  int(11) NULL COMMENT '付款/未付款' ,
`address`  varchar(256) NULL COMMENT '收货地址' ,
`tele`  bigint(20) NULL COMMENT '电话号码' ,
PRIMARY KEY (`id`)
)
;