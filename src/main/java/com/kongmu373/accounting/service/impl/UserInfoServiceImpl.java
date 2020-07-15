package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.UserInfoP2CConverter;
import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoDao userInfoDao;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoServiceImpl(UserInfoDao userInfoDao, UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDao = userInfoDao;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }


    @Override
    public UserInfoDTO getUserInfoByUserId(long id) {
        return userInfoP2CConverter.convert(userInfoDao.getUserInfoByUserId(id));
    }
}
