package com.kongmu373.accounting.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kongmu373.accounting.converter.p2c.TagP2CConverter;
import com.kongmu373.accounting.dao.TagDao;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.persistence.TagDo;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDao tagDao;

    private TagP2CConverter tagP2CConverter = new TagP2CConverter();

    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        tagService = new TagServiceImpl(tagDao, tagP2CConverter);
    }

    @AfterEach
    void tearDown() {
        reset(tagDao);
    }

    @Test
    void createTagThrowsInvalidParameterException() {
        // Arrange
        val tagId = 100L;
        val userId = 1900L;
        val description = "playing";
        val tag = TagDto.builder()
                      .id(tagId)
                      .description(description)
                      .userId(userId)
                      .status("ENABLE")
                      .build();
        val tagInDb = TagDo.builder()
                          .id(tagId)
                          .description(description)
                          .userId(userId)
                          .status(1)
                          .build();
        when(tagDao.getTagByDescriptionAndUserId(description, userId))
            .thenReturn(tagInDb);

        // act && assert
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            throw new InvalidParameterException(String.format(
                "The tag with description [%s] has been created on yourself.", tagInDb.getDescription()
            ));
        });
    }

    @Test
    void createTagWithHappyCase() {
        // Arrange
        val tagId = 100L;
        val userId = 1900L;
        val description = "playing";
        val tag = TagDto.builder()
                      .id(tagId)
                      .description(description)
                      .userId(userId)
                      .status("ENABLE")
                      .build();
        val tagInDb = TagDo.builder()
                          .id(tagId)
                          .description(description)
                          .userId(userId)
                          .status(1)
                          .build();
        when(tagDao.getTagByDescriptionAndUserId(description, userId))
            .thenReturn(null);

        // act
        tagService.createTag(tag);

        // assert
        verify(tagDao).createTag(any(TagDo.class));
    }
}