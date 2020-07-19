package com.kongmu373.accounting.controller;

import com.kongmu373.accounting.converter.c2s.UserInfoC2SConverter;
import com.kongmu373.accounting.exception.ErrorResponse;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.UserInfoVo;
import com.kongmu373.accounting.service.UserInfoService;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("v1.0/users")
@Slf4j
@Api(value = "UserInfo API")
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
public class UserInfoController {
    private final UserInfoC2SConverter userInfoC2SConverter;
    private final UserInfoService userInfoService;
    private static final int OK = 200;
    private static final int BAD_REQUEST = 400;
    private static final int NOT_FOUND = 404;

    @Autowired
    public UserInfoController(UserInfoC2SConverter userInfoC2SConverter, UserInfoService userInfoService) {
        this.userInfoC2SConverter = userInfoC2SConverter;
        this.userInfoService = userInfoService;
    }

    /**
     * Get user information by specific user id.
     *
     * @param id the user id
     * @return userInfo response entity
     */
    @ApiOperation(value = "get UserInfo by pass UserId", response = UserInfoVo.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = "获取用户信息成功", response = UserInfoVo.class),
        @ApiResponse(code = BAD_REQUEST, message = "输入参数有误", response = ErrorResponse.class),
        @ApiResponse(code = NOT_FOUND, message = "用户信息没有找到", response = ErrorResponse.class)})
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoVo> getUserInfoByUserId(@PathVariable("id") long id) {
        log.debug("get param id: {}", id);
        if (id <= 0L) {
            throw new InvalidParameterException(String.format("The user id %s is invalid", id));
        }
        UserInfoVo userInfoVO = userInfoC2SConverter.convert(userInfoService.getUserInfoByUserId(id));
        return ResponseEntity.ok(Objects.requireNonNull(userInfoVO));
    }

    /**
     * Register API with username and password.
     *
     * @param username specific username
     * @param password specific password
     * @return
     */
    @PostMapping
    public ResponseEntity<UserInfoVo> register(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {
        UserInfoDto userInfoDto = userInfoService.register(username, password);
        UserInfoVo userInfoVo = userInfoC2SConverter.convert(userInfoDto);
        return ResponseEntity.ok(Objects.requireNonNull(userInfoVo));
    }
}
