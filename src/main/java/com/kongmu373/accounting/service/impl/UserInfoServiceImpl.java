package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.UserInfoP2CConverter;
import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.exception.ResourceNotFoundException;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import com.kongmu373.accounting.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public UserInfoDto getUserInfoByUserId(long id) {
        UserInfoDo userinfo =
                Optional.ofNullable(userInfoDao.getUserInfoByUserId(id))
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("user_id %s was not found.", id)));
        return userInfoP2CConverter.convert(userinfo);
    }
}
