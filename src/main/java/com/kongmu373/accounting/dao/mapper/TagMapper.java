package com.kongmu373.accounting.dao.mapper;

import com.kongmu373.accounting.model.persistence.TagDo;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TagMapper {

    @Select("SELECT id, description, user_id, status, create_time, update_time FROM tally_tag "
                + "WHERE description = #{description} and user_id = #{userId}")
    @Results({
        @Result(column = "id", property = "id"),
        @Result(column = "description", property = "description"),
        @Result(column = "user_id", property = "userId"),
        @Result(column = "status", property = "status"),
        @Result(column = "create_time", property = "createTime"),
        @Result(column = "update_time", property = "updateTime"),
    })
    TagDo getTagByDescription(@Param("description") String description, @Param("userId") Long userId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO tally_tag(description, user_id, status) "
                + "VALUES (#{description}, #{userId}, #{status})")
    int insertTag(TagDo tag);
}
