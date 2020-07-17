package com.kongmu373.accounting.controller;

import com.kongmu373.accounting.converter.c2s.UserInfoC2SConverter;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.model.service.UserInfoVo;
import com.kongmu373.accounting.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1.0/users")
@Slf4j
public class UserInfoController {
    private final UserInfoC2SConverter userInfoC2SConverter;
    private final UserInfoService userInfoService;

    @Autowired
    public UserInfoController(UserInfoC2SConverter userInfoC2SConverter, UserInfoService userInfoService) {
        this.userInfoC2SConverter = userInfoC2SConverter;
        this.userInfoService = userInfoService;
    }

    /**
     * Get user information by specific user id.
     * @param id the user id
     * @return userInfo response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoVo> getUserInfoByUserId(@PathVariable("id") long id) {
        log.debug("get param id: {}", id);
        if (id <= 0L) {
            throw new InvalidParameterException(String.format("The user id %s is invalid", id));
        }
        UserInfoVo userInfoVO = userInfoC2SConverter.convert(userInfoService.getUserInfoByUserId(id));
        return ResponseEntity.ok(userInfoVO);
    }
}
