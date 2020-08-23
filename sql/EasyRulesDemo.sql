##搭建mybatis-puls所用的demo

DROP TABLE IF EXISTS user;

CREATE TABLE user
(
  id BIGINT(20) NOT NULL COMMENT '主键ID',
  name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age INT(11) NULL DEFAULT NULL COMMENT '年龄',
  email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (id)
);


DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');


#easyrule数据库
CREATE TABLE `sys_er_rules` (
      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
      `er_name` varchar(60) DEFAULT NULL,
      `er_description` varchar(60) DEFAULT NULL,
      `er_condition` varchar(120) DEFAULT NULL,
      `er_actions` text,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;