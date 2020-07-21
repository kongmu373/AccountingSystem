package com.kongmu373.accounting.controller;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kongmu373.accounting.converter.c2s.UserInfoC2SConverter;
import com.kongmu373.accounting.exception.GlobalExceptionHandler;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class UserInfoControllerTest {

    private UserInfoC2SConverter userInfoC2SConverter = new UserInfoC2SConverter();

    @Mock
    private UserInfoService userInfoService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserInfoController(userInfoC2SConverter,
            userInfoService))
                      .setControllerAdvice(GlobalExceptionHandler.class)
                      .build();
    }

    @AfterEach
    void tearDown() {
        reset(userInfoService);
    }

    @Test
    void testGetUserInfoByUserIdWithHappyCase() throws Exception {
        // Arrange
        long userId = 1L;
        String username = "admin";
        String password = "admin";
        UserInfoDto userInfoDTO = UserInfoDto.builder()
                                      .id(userId)
                                      .username(username)
                                      .password(password)
                                      .build();
        doReturn(userInfoDTO).when(userInfoService).getUserInfoByUserId(userId);
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/" + userId))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"id\":1,\"username\":\"admin\"}"));
        verify(userInfoService).getUserInfoByUserId(userId);
    }

    @Test
    void testGetUserInfoByUserIdThrowsInvalidParameterException() throws Exception {
        // Arrange
        long userId = -1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/users/" + userId))
            .andExpect(status().isBadRequest())
            .andExpect(content().string(
                "{\"code\":\"INVALID_PARAMETER\",\"errorType\":\"Client\",\"message\":\"The user id -1 is invalid\",\"statusCode\":400}"));
    }

    @Test
    void testRegisterWithHappyCase() throws Exception {

        UserInfoDto user = UserInfoDto.builder()
                               .id(1L)
                               .username("test")
                               .build();
        // Arrange
        doReturn(user).when(userInfoService).register("test", "test");

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/v1.0/users")
                            .param("username", "test")
                            .param("password", "test"))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"id\":1,\"username\":\"test\"}"));

        verify(userInfoService).register("test", "test");
    }


}