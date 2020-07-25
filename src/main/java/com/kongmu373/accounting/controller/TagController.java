package com.kongmu373.accounting.controller;

import com.kongmu373.accounting.converter.c2s.TagC2SConverter;
import com.kongmu373.accounting.exception.InvalidParameterException;
import com.kongmu373.accounting.model.common.TagDto;
import com.kongmu373.accounting.model.common.UserInfoDto;
import com.kongmu373.accounting.model.service.TagVo;
import com.kongmu373.accounting.service.TagService;
import com.kongmu373.accounting.service.UserInfoService;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("v1.0/tags")
@Slf4j
@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
public class TagController {

    private final UserInfoService userInfoService;
    private final TagService tagService;
    private final TagC2SConverter tagC2SConverter;

    /**
     * The constructor of the tag module.
     *
     * @param userInfoService userInfoService
     * @param tagService      tagService
     * @param tagC2SConverter tagDto convert to tagVo
     */
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

    /**
     * get Tag by tag_id.
     *
     * @param id tag id.
     * @return tagVo
     */
    @GetMapping("/{id}")
    public ResponseEntity<TagVo> getTagById(@PathVariable("id") long id) {
        log.debug("tag id : {}", id);
        if (id <= 0L) {
            throw new InvalidParameterException("The tagId must be not empty and positive.");
        }
        val tagDto = tagService.getTagById(id);
        TagVo convert = tagC2SConverter.convert(tagDto);
        return ResponseEntity.ok(Objects.requireNonNull(convert));
    }

    /**
     * Update tag information,the tagVo parameters allowed to be modified include:
     * 1. description
     * 2. status
     *
     * @param id    Id of the tag to be modified
     * @param tagVo Information of the tag to be modified
     * @return
     */
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<TagVo> updateTag(@PathVariable("id") long id,
                                           @RequestBody TagVo tagVo) {
        if (id <= 0L) {
            throw new InvalidParameterException("The tagId must be not empty and positive.");
        }
        String status = tagVo.getStatus();
        if (status != null && !"ENABLE".equals(status) && !"DISABLE".equals(status)) {
            throw new InvalidParameterException(String.format("The status [%s] to update is invalid status", status));
        }

        // get User id.
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UserInfoDto user = userInfoService.getUserInfoByUserName(username);
        log.debug("subject: {}", username);

        // reset TagVo
        tagVo.setUserId(user.getId());
        tagVo.setId(id);

        val tagDto = tagC2SConverter.reverse().convert(tagVo);
        val res = tagService.updateTag(tagDto);
        TagVo convert = tagC2SConverter.convert(res);
        return ResponseEntity.ok(Objects.requireNonNull(convert));
    }
}
