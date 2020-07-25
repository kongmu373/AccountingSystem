DROP TABLE IF EXISTS `tally_tag`;
CREATE TABLE `tally_tag`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `description` varchar(50)         NOT NULL,
    `user_id`     bigint(20) unsigned NOT NULL,
    `status`      tinyint(1) unsigned NOT NULL COMMENT '0-> disabled, 1-> enabled',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`),
    KEY `idx_uid` (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES tally_userinfo (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

INSERT INTO tally_tag(description, user_id, status) VALUES ('eat', 1, 1);

INSERT INTO tally_tag(description, user_id, status) VALUES ('playing', 1, 1);

