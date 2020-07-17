package com.kongmu373.accounting.converter.c2s;

import com.google.common.base.Converter;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.UserInfoVo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoC2SConverter extends Converter<UserInfoDto, UserInfoVo> {

    @Override
    protected UserInfoVo doForward(UserInfoDto userInfoDto) {
        return UserInfoVo.builder()
                       .id(userInfoDto.getId())
                       .username(userInfoDto.getUsername())
                       .build();
    }

    @Override
    protected UserInfoDto doBackward(UserInfoVo userInfoVO) {
        return UserInfoDto.builder()
                       .id(userInfoVO.getId())
                       .username(userInfoVO.getUsername())
                       .password(userInfoVO.getPassword())
                       .build();
    }
}
