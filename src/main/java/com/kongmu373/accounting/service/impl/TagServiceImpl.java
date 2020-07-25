package com.kongmu373.accounting.service.impl;

import com.kongmu373.accounting.converter.p2c.TagP2CConverter;
import com.kongmu373.accounting.dao.TagDao;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.exception.UnAuthorizedException;
import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.persistence.TagDo;
import com.kongmu373.accounting.service.TagService;

import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagP2CConverter tagP2CConverter;

    public TagServiceImpl(TagDao tagDao, TagP2CConverter tagP2CConverter) {
        this.tagDao = tagDao;
        this.tagP2CConverter = tagP2CConverter;
    }


    @Override
    public TagDto createTag(TagDto tagDto) {
        Optional.ofNullable(tagDao.getTagByDescriptionAndUserId(tagDto.getDescription(), tagDto.getUserId()))
            .ifPresent((tagDo) -> {
                throw new InvalidParameterException(String.format(
                    "The tag with description [%s] has been created on yourself.", tagDo.getDescription()
                ));
            });
        val tagDo = tagP2CConverter.reverse().convert(tagDto);
        tagDao.createTag(tagDo);
        return tagP2CConverter.convert(tagDo);
    }

    @Override
    public TagDto getTagById(long id) {
        TagDo tagDo = Optional.ofNullable(tagDao.getTagById(id)).orElseThrow(
            () -> new InvalidParameterException(String.format("The tag with id [%s] has not been created .", id)));

        return tagP2CConverter.convert(tagDo);
    }

    @Override
    public TagDto updateTag(TagDto tagDto) {
        // Check if the tag exists.
        TagDto tagInDB = getTagById(tagDto.getId());
        if (!Objects.equals(tagDto.getUserId(), tagInDB.getUserId())) {
            throw new UnAuthorizedException(String.format("The tag id [%s] doesn't belong to user id: [%s]",
                tagDto.getId(), tagDto.getUserId()));
        }
        TagDo convert = tagP2CConverter.reverse().convert(tagDto);
        tagDao.updateTag(convert);
        return getTagById(tagDto.getId());
    }
}
