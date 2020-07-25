package com.kongmu373.accounting.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kongmu373.accounting.dao.TagDao;
import com.kongmu373.accounting.dao.mapper.TagMapper;
import com.kongmu373.accounting.model.persistence.TagDo;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;


@ExtendWith(MockitoExtension.class)
class TagDaoImplTest {

    @Mock
    private TagMapper tagMapper;

    private TagDaoImpl tagDao;

    @BeforeEach
    void setUp() {
        tagDao = new TagDaoImpl(tagMapper);
    }

    @AfterEach
    void tearDown() {
        reset(tagMapper);
    }

    @Test
    void getTagByDescriptionAndUserId() {
        // Arrange
        val tagId = 1000L;
        val userId = 100L;
        val description = "playing";
        val createTime = LocalDateTime.now();

        val tag = TagDo.builder()
                      .id(tagId)
                      .userId(userId)
                      .description(description)
                      .status(1)
                      .createTime(createTime)
                      .build();
        when(tagMapper.getTagByDescription(description, userId))
            .thenReturn(tag);

        // Act
        val result = tagDao.getTagByDescriptionAndUserId(description, userId);
        // Assert
        assertEquals(tag, result);
        verify(tagMapper).getTagByDescription(description, userId);
    }

    @Test
    void createTag() {
        // Arrange
        val tagId = 1000L;
        val userId = 100L;
        val description = "playing";
        val createTime = LocalDateTime.now();

        val tag = TagDo.builder()
                      .id(tagId)
                      .userId(userId)
                      .description(description)
                      .status(1)
                      .createTime(createTime)
                      .build();

        // Act
        tagDao.createTag(tag);
        // Assert
        verify(tagMapper).insertTag(any(TagDo.class));
    }

    @Test
    void updateTag() {
        // Arrange
        val tagId = 1000L;
        val userId = 100L;
        val description = "playing";
        val createTime = LocalDateTime.now();

        val tag = TagDo.builder()
                      .id(tagId)
                      .userId(userId)
                      .description(description)
                      .status(1)
                      .createTime(createTime)
                      .build();

        // Act
        tagDao.updateTag(tag);

        // Verify
        tagMapper.updateTag(tag);
    }

    @Test
    void testGetTagById() {
        // Arrange
        val tagId = 1000L;

        // Act
        tagDao.getTagById(tagId);
        // Assert
        verify(tagMapper).getTagById(tagId);
    }
}