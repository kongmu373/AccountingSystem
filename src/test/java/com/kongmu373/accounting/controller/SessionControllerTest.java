package com.kongmu373.accounting.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kongmu373.accounting.exception.GlobalExceptionHandler;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.service.UserInfoService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {

    @Mock
    UserInfoService userInfoService;


    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SessionController(userInfoService))
                      .setControllerAdvice(GlobalExceptionHandler.class)
                      .build();
    }

    @AfterEach
    void tearDown() {
        reset(userInfoService);
    }

    @Test
    void testLogin() throws Exception {
        // Arrange
        val username = "test";
        val password = "test";

        val userInfo = UserInfoDto.builder()
                           .username(username)
                           .password(password)
                           .build();

        doNothing().when(userInfoService).login(username, password);

        // Act && Assert
        mockMvc.perform(post("/v1.0/session")
                            .contentType("application/json")
                            .content(new ObjectMapper().writeValueAsString(userInfo))
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string("{\"status\":\"success\",\"username\":\"test\"}"));

        verify(userInfoService).login(username, password);
    }
}