package com.kongmu373.accounting.service;

import com.kongmu373.accounting.model.common.TagDto;

public interface TagService {
    TagDto createTag(TagDto tagDto);

    TagDto getTagById(long id);

    TagDto updateTag(TagDto tagDto);
}
