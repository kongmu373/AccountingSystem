package com.kongmu373.accounting.dao;

import com.kongmu373.accounting.model.persistence.TagDo;

public interface TagDao {

    TagDo getTagByDescriptionAndUserId(String description, long userId);

    void createTag(TagDo tagDo);
}
