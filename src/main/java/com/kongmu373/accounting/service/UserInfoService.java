package com.kongmu373.accounting.service;

import com.kongmu373.accounting.model.common.UserInfoDTO;

public interface UserInfoService {

    UserInfoDTO getUserInfoByUserId(long id);
}
