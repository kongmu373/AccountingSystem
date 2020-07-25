package com.kongmu373.accounting.converter.c2s;

import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.service.TagVo;

import com.google.common.base.Converter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TagC2SConverter extends Converter<TagDto, TagVo> {
    @Override
    protected TagVo doForward(TagDto tagDto) {
        return TagVo.builder()
                   .description(tagDto.getDescription())
                   .id(tagDto.getId())
                   .userId(tagDto.getUserId())
                   .status(tagDto.getStatus())
                   .build();
    }

    @Override
    protected TagDto doBackward(TagVo tagVo) {
        return TagDto.builder()
                   .id(tagVo.getId())
                   .userId(tagVo.getUserId())
                   .description(tagVo.getDescription())
                   .status(tagVo.getStatus())
                   .build();
    }
}
