package com.kongmu373.accounting.dao;

import com.kongmu373.accounting.model.persistence.UserInfoDo;

public interface UserInfoDao {

    UserInfoDo getUserInfoByUserId(long id);
}
