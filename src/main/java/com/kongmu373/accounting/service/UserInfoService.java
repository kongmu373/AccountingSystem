package com.kongmu373.accounting.service;

import com.kongmu373.accounting.model.common.UserInfoDto;

public interface UserInfoService {

    UserInfoDto getUserInfoByUserId(long id);
}
