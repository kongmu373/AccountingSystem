package com.kongmu373.accounting.converter.p2c;

import com.google.common.base.Converter;
import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.model.persistence.UserInfoDO;
import org.springframework.stereotype.Component;

@Component
public class UserInfoP2CConverter extends Converter<UserInfoDO, UserInfoDTO> {
    @Override
    protected UserInfoDTO doForward(UserInfoDO userInfoDO) {
        return UserInfoDTO.builder()
                       .id(userInfoDO.getId())
                       .username(userInfoDO.getUsername())
                       .password(userInfoDO.getPassword())
                       .build();
    }

    @Override
    protected UserInfoDO doBackward(UserInfoDTO userInfoDTO) {
        return UserInfoDO.builder()
                       .id(userInfoDTO.getId())
                       .username(userInfoDTO.getUsername())
                       .password(userInfoDTO.getPassword())
                       .build();
    }
}
