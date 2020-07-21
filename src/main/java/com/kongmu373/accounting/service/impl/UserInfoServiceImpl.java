package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.UserInfoP2CConverter;
import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.exception.ResourceNotFoundException;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    public static final int HASH_ITERATIONS = 1000;
    private final UserInfoDao userInfoDao;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoServiceImpl(UserInfoDao userInfoDao, UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDao = userInfoDao;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }


    @Override
    public UserInfoDto getUserInfoByUserId(long id) {

        return Optional.ofNullable(userInfoDao.getUserInfoByUserId(id))
                   .map(userInfoP2CConverter::convert)
                   .orElseThrow(() -> new ResourceNotFoundException(
                       String.format("user_id %s was not found.", id)));
    }

    @Override
    public UserInfoDto getUserInfoByUserName(String userName) {
        return Optional.ofNullable(userInfoDao.getUserInfoByUserName(userName))
                   .map(userInfoP2CConverter::convert)
                   .orElseThrow(() -> new ResourceNotFoundException(
                       String.format("user_name %s was not found.", userName)));
    }

    @Override
    public void login(String username, String password) {
        // Get subject
        val subject = SecurityUtils.getSubject();
        // Construct token.
        val usernamePasswordToken = new UsernamePasswordToken(username, password);
        // login
        subject.login(usernamePasswordToken);
    }

    @Override
    public UserInfoDto register(String username, String password) {
        UserInfoDo userInfo = userInfoDao.getUserInfoByUserName(username);
        if (userInfo != null) {
            throw new InvalidParameterException(String.format("The user %s was registered.", username));
        }

        //simulate a user account with a SHA-1 hashed and salted password:
        String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
        String encryptedPassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();

        UserInfoDo newUserInfo = UserInfoDo.builder()
                                     .username(username)
                                     .password(encryptedPassword)
                                     .salt(salt)
                                     .createTime(LocalDate.now())
                                     .build();
        userInfoDao.createUserInfo(newUserInfo);
        return userInfoP2CConverter.convert(newUserInfo);
    }
}
