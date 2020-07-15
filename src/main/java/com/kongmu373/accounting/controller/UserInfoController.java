package com.kongmu373.accounting.controller;

import com.kongmu373.accounting.converter.c2s.UserInfoC2SConverter;
import com.kongmu373.accounting.model.service.UserInfoVO;
import com.kongmu373.accounting.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users")
@Slf4j
public class UserInfoController {
    private final UserInfoC2SConverter userInfoC2SConverter;
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoC2SConverter userInfoC2SConverter, UserInfoService userInfoService) {
        this.userInfoC2SConverter = userInfoC2SConverter;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{id}")
    public UserInfoVO getUserInfoByUserId(@PathVariable("id") long id) {
        log.debug("get param id: {}", id);
        return userInfoC2SConverter.convert(userInfoService.getUserInfoByUserId(id));
    }
}
