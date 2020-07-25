package com.kongmu373.accounting.converter.p2c;

import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.persistence.TagDo;

import com.google.common.base.Converter;
import com.sun.istack.internal.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TagP2CConverter extends Converter<TagDo, TagDto> {

    private static final String ENABLE = "ENABLE";
    private static final String DISABLE = "DISABLE";

    @Override
    protected TagDto doForward(@NotNull TagDo tagDo) {
        return TagDto.builder()
                   .id(tagDo.getId())
                   .description(tagDo.getDescription())
                   .status(tagDo.getStatus() == 1 ? ENABLE : DISABLE)
                   .userId(tagDo.getUserId())
                   .build();
    }

    @Override
    protected TagDo doBackward(TagDto tagDto) {

        val tagDoInDb = TagDo.builder()
                            .id(tagDto.getId())
                            .description(tagDto.getDescription())
                            .userId(tagDto.getUserId())
                            .build();

        if (tagDto.getStatus() != null) {
            tagDoInDb.setStatus(ENABLE.equals(tagDto.getStatus()) ? 1 : 0);
        }

        return tagDoInDb;
    }
}