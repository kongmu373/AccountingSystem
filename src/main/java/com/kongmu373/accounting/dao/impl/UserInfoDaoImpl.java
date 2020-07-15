package com.kongmu373.accounting.dao.impl;

import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.dao.mapper.UserInfoMapper;
import com.kongmu373.accounting.model.persistence.UserInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoDaoImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfoDO getUserInfoByUserId(long id) {
        return userInfoMapper.getUserInfoByUserId(id);
    }
}
