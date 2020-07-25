package com.kongmu373.accounting.controller;

import com.kongmu373.accounting.converter.c2s.TagC2SConverter;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.TagVo;
import com.kongmu373.accounting.service.TagService;
import com.kongmu373.accounting.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("v1.0/tags")
@Slf4j
public class TagController {

    private final UserInfoService userInfoService;
    private final TagService tagService;
    private final TagC2SConverter tagC2SConverter;

    @Autowired
    public TagController(UserInfoService userInfoService, TagService tagService,
                         TagC2SConverter tagC2SConverter) {
        this.userInfoService = userInfoService;
        this.tagService = tagService;
        this.tagC2SConverter = tagC2SConverter;
    }

    /**
     * Create tag.
     *
     * @param tagVo tag
     * @return
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<TagVo> createTag(@RequestBody TagVo tagVo) {
        log.debug("tagVo: {}", tagVo);
        if (StringUtils.isEmpty(tagVo.getDescription())) {
            throw new InvalidParameterException("description is empty");
        }
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UserInfoDto user = userInfoService.getUserInfoByUserName(username);
        log.debug("subject: {}", username);
        tagVo.setUserId(user.getId());
        TagDto convert = tagC2SConverter.reverse().convert(tagVo);
        val tagDto = tagService.createTag(convert);
        return ResponseEntity.ok(Objects.requireNonNull(tagC2SConverter.convert(tagDto)));
    }
}
