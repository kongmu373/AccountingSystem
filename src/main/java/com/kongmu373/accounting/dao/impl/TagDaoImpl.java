package com.kongmu373.accounting.dao.impl;

import com.kongmu373.accounting.dao.TagDao;
import com.kongmu373.accounting.dao.mapper.TagMapper;
import com.kongmu373.accounting.model.persistence.TagDo;

import org.springframework.stereotype.Repository;

@Repository
public class TagDaoImpl implements TagDao {

    private final TagMapper tagMapper;

    public TagDaoImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public TagDo getTagByDescriptionAndUserId(String description, long userId) {
        return tagMapper.getTagByDescription(description, userId);
    }

    @Override
    public void createTag(TagDo tagDo) {
        tagMapper.insertTag(tagDo);
    }

    @Override
    public TagDo getTagById(long id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public void updateTag(TagDo tagDo) {
        tagMapper.updateTag(tagDo);
    }
}
