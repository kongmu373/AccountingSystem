package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.UserInfoP2CConverter;
import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.dao.mapper.UserInfoMapper;
import com.kongmu373.accounting.exception.ResourceNotFoundException;
import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.model.persistence.UserInfoDO;
import com.kongmu373.accounting.service.UserInfoService;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserInfoServiceImplTest {

    UserInfoService userInfoService;

    @Mock
    UserInfoMapper userInfoMapper;

    @Mock
    UserInfoDao userInfoDao;

    UserInfoP2CConverter userInfoP2CConverter = new UserInfoP2CConverter();

    @BeforeEach
    public void setUp() {
        initMocks(this);
        userInfoService = new UserInfoServiceImpl(userInfoDao, userInfoP2CConverter);
    }

    @Test
    public void testGetUserInfoByUserIdMethodWhenHappyCase() {
        // Arrange
        long userId = 1L;
        val username = "admin";
        val password = "admin";
        Instant now = Instant.now();
        UserInfoDO userInfoDO = UserInfoDO.builder()
                                        .id(userId)
                                        .username(username)
                                        .password(password)
                                        .create_time(now)
                                        .build();
        doReturn(userInfoDO).when(userInfoDao).getUserInfoByUserId(userId);
        // Act
        UserInfoDTO info = userInfoService.getUserInfoByUserId(userId);
        // Assert
        assertThat(info).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
        verify(userInfoDao).getUserInfoByUserId(userId);
    }

    @Test
    public void testGetUserInfoByUserIdMethodWhenInvalidUserId() {
        // Arrange
        val userId = 1L;
        doReturn(null).when(userInfoDao).getUserInfoByUserId(userId);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userInfoService.getUserInfoByUserId(userId));
        verify(userInfoDao).getUserInfoByUserId(userId);
    }


}