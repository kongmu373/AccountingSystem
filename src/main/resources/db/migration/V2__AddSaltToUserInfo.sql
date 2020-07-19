ALTER TABLE `tally_userinfo`
    ADD COLUMN (`salt` varchar(50) NOT NULL);

-- INSERT `tally_userinfo` value (NULL, 'admin', '123', NOW(), NULL);
--
--
-- UPDATE `tally_userinfo` SET password='admin' where username = 'admin';
