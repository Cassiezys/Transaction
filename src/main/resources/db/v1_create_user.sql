CREATE TABLE `table` (
    content_user/production
)
;

content_user
{
    `id`  bigint(20) NOT NULL AUTO_INCREMENT ,
    `account_id`  varchar(100) NOT NULL ,
    `password`  varchar(30) NOT NULL ,
    `name`  varchar(100) NULL ,
    `token`  char(36) NULL ,
    `gmt_create`  bigint(20) NULL ,
    `gmt_modified`  bigint(20) NULL ,
    `bio`  varchar(256) NULL COMMENT '简介' ,
    `avatar_url`  varchar(100) NULL COMMENT '头像地址' ,
    PRIMARY KEY (`id`)
}
content_production
{
`id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8 DEFAULT NULL,
  `description` text,
  `gmt_create` bigint(20) DEFAULT NULL,
  `gmt_modified` bigint(20) DEFAULT NULL,
  `creator` bigint(20) DEFAULT NULL COMMENT '卖家id',
  `comment_count` int(11) DEFAULT NULL COMMENT '评论数',
  `view_count` int(11) DEFAULT NULL,
  `like_count` int(11) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `pic_url` varchar(50) DEFAULT NULL COMMENT '图片地址',
  `price` float(20,2) DEFAULT NULL,
  `origprice` float(20,2) DEFAULT NULL,
  `tele` bigint(20) DEFAULT NULL,
  `tencent` bigint(20) DEFAULT NULL,
  `wechat` varchar(30) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `payway` varchar(64) DEFAULT NULL COMMENT '支付方式',
  `category` varchar(64) DEFAULT NULL COMMENT '商品类别：手机/电脑',
  PRIMARY KEY (`id`)
}
 private Long id,creator
 String title,description;
     private Long gmtCreate;
     private Long gmtModified;
  int commentCount
  int viewCount
  int likeCount
  string city,picUrl;
  float price,origprice;
  long tele,tencent;
  String wechat,address,payway,category;