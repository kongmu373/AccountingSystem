DROP TABLE IF EXISTS `tally_record`;
CREATE TABLE `tally_record`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `amount`      decimal(18,2)       NOT NULL DEFAULT '0.00',
    `note`        varchar(200)        DEFAULT NULL,
    `category`    tinyint(1) unsigned NOT NULL COMMENT '0-> outcome, 1-> income',
    `status`      tinyint(1) unsigned NOT NULL COMMENT '0-> disabled, 1-> enabled',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    `user_id`     bigint(20) unsigned NOT NULL,
    PRIMARY KEY `pk_id` (`id`),
    KEY `user_key` (`user_id`),
    FOREIGN KEY (`user_id`) REFERENCES tally_userinfo (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;