package com.kongmu373.accounting.dao;

import com.kongmu373.accounting.model.persistence.UserInfoDO;

public interface UserInfoDao {

    UserInfoDO getUserInfoByUserId(long id);
}
