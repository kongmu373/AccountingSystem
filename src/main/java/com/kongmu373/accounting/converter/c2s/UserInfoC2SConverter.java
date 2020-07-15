package com.kongmu373.accounting.converter.c2s;

import com.google.common.base.Converter;
import com.kongmu373.accounting.model.common.UserInfoDTO;
import com.kongmu373.accounting.model.service.UserInfoVO;
import org.springframework.stereotype.Component;

@Component
public class UserInfoC2SConverter extends Converter<UserInfoDTO, UserInfoVO> {

    @Override
    protected UserInfoVO doForward(UserInfoDTO userInfoDTO) {
        return UserInfoVO.builder()
                       .id(userInfoDTO.getId())
                       .username(userInfoDTO.getUsername())
                       .build();
    }

    @Override
    protected UserInfoDTO doBackward(UserInfoVO userInfoVO) {
        return UserInfoDTO.builder()
                       .id(userInfoVO.getId())
                       .username(userInfoVO.getUsername())
                       .password(userInfoVO.getPassword())
                       .build();
    }
}
