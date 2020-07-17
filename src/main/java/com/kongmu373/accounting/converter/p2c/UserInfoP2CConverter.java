package com.kongmu373.accounting.converter.p2c;

import com.google.common.base.Converter;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.persistence.UserInfoDo;
import org.springframework.stereotype.Component;

@Component
public class UserInfoP2CConverter extends Converter<UserInfoDo, UserInfoDto> {
    @Override
    protected UserInfoDto doForward(UserInfoDo userInfoDO) {
        return UserInfoDto.builder()
                       .id(userInfoDO.getId())
                       .username(userInfoDO.getUsername())
                       .password(userInfoDO.getPassword())
                       .build();
    }

    @Override
    protected UserInfoDo doBackward(UserInfoDto userInfoDto) {
        return UserInfoDo.builder()
                       .id(userInfoDto.getId())
                       .username(userInfoDto.getUsername())
                       .password(userInfoDto.getPassword())
                       .build();
    }
}
