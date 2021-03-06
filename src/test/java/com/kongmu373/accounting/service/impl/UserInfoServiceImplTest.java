package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.UserInfoP2CConverter;
import com.kongmu373.accounting.dao.UserInfoDao;
import com.kongmu373.accounting.exception.ResourceNotFoundException;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserInfoServiceImplTest {

    UserInfoService userInfoService;

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
        LocalDate now = LocalDate.now();
        UserInfoDo userInfoDO = UserInfoDo.builder()
                                    .id(userId)
                                    .username(username)
                                    .password(password)
                                    .createTime(now)
                                    .build();
        doReturn(userInfoDO).when(userInfoDao).getUserInfoByUserId(userId);
        // Act
        UserInfoDto info = userInfoService.getUserInfoByUserId(userId);
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

    @Test
    public void testGetUserInfoByUserNameWhenHappyCase() {
        // Arrange
        val username = "test";
        val userInfoDo = UserInfoDo.builder()
                             .username("test")
                             .password("test")
                             .build();
        val userInfoDto = UserInfoDto.builder()
                              .username("test")
                              .password("test")
                              .build();
        doReturn(userInfoDo).when(userInfoDao).getUserInfoByUserName(username);
        // Act
        UserInfoDto userInfoByUserName = userInfoService.getUserInfoByUserName(username);

        // assert
        assertEquals(userInfoDto, userInfoByUserName);
        verify(userInfoDao).getUserInfoByUserName(username);
    }

    @Test
    public void testGetUserInfoByUserNameThrowsResourceNotFoundException() {
        // Arrange
        val username = "test";

        doReturn(null).when(userInfoDao).getUserInfoByUserName(username);
        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
            () -> userInfoService.getUserInfoByUserName(username));
    }

}