package com.kongmu373.accounting.service;

import com.kongmu373.accounting.model.common.UserInfoDto;

public interface UserInfoService {
    /**
     * get UserInfo By pass the specific userId.
     *
     * @param id the specific userId
     * @return return UserInfoDto
     */
    UserInfoDto getUserInfoByUserId(long id);

    /**
     * get UserInfo By pass the specific userName.
     *
     * @param userName the specific userName
     * @return return UserInfoDto
     */
    UserInfoDto getUserInfoByUserName(String userName);

    /**
     * Login with username and password.
     *
     * @param username username
     * @param password the related password
     */
    void login(String username, String password);

    /**
     * Register with username and password.
     *
     * @param username username
     * @param password the related password
     */
    UserInfoDto register(String username, String password);
}
