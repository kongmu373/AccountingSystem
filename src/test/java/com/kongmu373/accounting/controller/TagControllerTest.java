package com.kongmu373.accounting.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kongmu373.accounting.converter.c2s.TagC2SConverter;
import com.kongmu373.accounting.exception.GlobalExceptionHandler;
import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.TagVo;
import com.kongmu373.accounting.service.TagService;
import com.kongmu373.accounting.service.UserInfoService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    @Mock
    UserInfoService userInfoService;
    @Mock
    TagService tagService;

    TagC2SConverter tagC2SConverter = new TagC2SConverter();

    TagController tagController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        tagController = new TagController(userInfoService, tagService, tagC2SConverter);
        mockMvc = MockMvcBuilders.standaloneSetup(tagController)
                      .setControllerAdvice(GlobalExceptionHandler.class)
                      .build();
        org.apache.shiro.mgt.SecurityManager securityManger =
            mock(org.apache.shiro.mgt.SecurityManager.class, RETURNS_DEEP_STUBS);
        ThreadContext.bind(securityManger);
    }

    @AfterEach
    void tearDown() {
        reset(userInfoService);
        reset(tagService);
    }

    @Test
    void createTagWithEmptyDescriptionThenThrowsException() throws Exception {
        // Arrange
        TagVo tagVo = TagVo.builder().build();

        // Act && Assert
        mockMvc.perform(post("/v1.0/tags")
                            .content(new ObjectMapper().writeValueAsString(tagVo))
                            .contentType("application/json"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    void createTagWithHappyCase() throws Exception {
        val currentUsername = "test";
        val currentUserId = 1L;
        val description = "test";

        TagVo build = TagVo.builder()
                          .description(description)
                          .build();
        val tagDto = TagDto.builder()
                         .id(1L)
                         .userId(1L)
                         .description(description)
                         .build();
        val tagVo = TagVo.builder()
                        .id(1L)
                        .userId(1L)
                        .description(description)
                        .build();
        UserInfoDto userInfoDto = UserInfoDto.builder()
                                      .username(currentUsername)
                                      .id(currentUserId)
                                      .build();
        when(SecurityUtils.getSubject().getPrincipal()).thenReturn(currentUsername);
        when(userInfoService.getUserInfoByUserName(currentUsername))
            .thenReturn(userInfoDto);
        when(tagService.createTag(any(TagDto.class))).thenReturn(tagDto);
        // Act && Assert

        mockMvc.perform(post("/v1.0/tags")
                            .content(mapper.writeValueAsString(build))
                            .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(mapper.writeValueAsString(tagVo)));
    }


    @Test
    void getTagById() throws Exception {
        // Arrange
        val currentUsername = "test";
        val currentUserId = 1L;
        val description = "test";

        TagVo build = TagVo.builder()
                          .description(description)
                          .build();
        val tagDto = TagDto.builder()
                         .id(1L)
                         .userId(1L)
                         .description(description)
                         .build();
        val tagVo = TagVo.builder()
                        .id(1L)
                        .userId(1L)
                        .description(description)
                        .build();
        when(tagService.getTagById(1L)).thenReturn(tagDto);

        // Act && Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1.0/tags/" + 1L))
            .andExpect(status().isOk())
            .andExpect(content().string(mapper.writeValueAsString(tagVo)));

        verify(tagService).getTagById(1L);
    }

    @Test
    void testUpdateTagWithHappyCase() throws Exception {
        // Arrange
        val currentUsername = "test";
        val currentUserId = 1L;
        val description = "test";

        TagVo build = TagVo.builder()
                          .description(description)
                          .build();
        val tagDto = TagDto.builder()
                         .id(1L)
                         .userId(1L)
                         .description(description)
                         .build();
        val tagVo = TagVo.builder()
                        .id(1L)
                        .userId(1L)
                        .description(description)
                        .build();
        UserInfoDto userInfoDto = UserInfoDto.builder()
                                      .username(currentUsername)
                                      .id(currentUserId)
                                      .build();
        when(SecurityUtils.getSubject().getPrincipal()).thenReturn(currentUsername);
        when(userInfoService.getUserInfoByUserName(currentUsername))
            .thenReturn(userInfoDto);
        when(tagService.updateTag(any(TagDto.class))).thenReturn(tagDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1.0/tags/" + 1L)
                            .content(new ObjectMapper().writeValueAsString(build))
                            .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(mapper.writeValueAsString(tagVo)));
    }
}