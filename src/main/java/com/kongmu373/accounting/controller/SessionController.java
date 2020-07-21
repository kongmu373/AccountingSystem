package com.kongmu373.accounting.controller;


import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("v1.0/session")
public class SessionController {
    private static final String SUCCESS = "success";
    private static final String STATUS = "status";
    private static final String USERNAME = "username";
    private static final int OK = 200;
    private static final int NOT_FOUND = 400;
    private final UserInfoService userInfoService;

    @Autowired
    public SessionController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    /**
     * Login with username and password.
     *
     * @param userInfoDto the specific userInfoDto includes username and password
     * @return The return for login
     */
    @PostMapping(value = "", produces = "application/json", consumes = "application/json")
    public Map<String, String> login(@RequestBody UserInfoDto userInfoDto) {
        val response = new HashMap<String, String>();
        userInfoService.login(userInfoDto.getUsername(), userInfoDto.getPassword());
        response.put(STATUS, SUCCESS);
        response.put(USERNAME, userInfoDto.getUsername());
        return response;
    }
}
