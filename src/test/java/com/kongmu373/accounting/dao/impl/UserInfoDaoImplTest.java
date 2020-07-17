package com.kongmu373.accounting.dao.impl;

import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.dao.mapper.UserInfoMapper;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserInfoDaoImplTest {

    @Mock
    private UserInfoMapper userInfoMapper;

    private UserInfoDao userInfoDao;

    @BeforeEach
    public void setUp() {
        userInfoDao = new UserInfoDaoImpl(userInfoMapper);
    }

    @Test
    public void testGetUserInfoByUserIdMethodWhenHappyCase() {
        // Arrange
        val userId = 100L;
        val username = "admin";
        val password = "admin";
        LocalDate now = LocalDate.now();
        UserInfoDo userInfoDO = UserInfoDo.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .createTime(now)
                                        .build();
        doReturn(userInfoDO).when(userInfoMapper).getUserInfoByUserId(userId);
        // Act
        val result = userInfoDao.getUserInfoByUserId(userId);
        // Assert
        assertEquals(userInfoDO, result);
        verify(userInfoMapper).getUserInfoByUserId(userId);
    }
}