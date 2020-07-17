package com.kongmu373.accounting.dao.mapper;

import com.kongmu373.accounting.model.persistence.UserInfoDo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select id, username, password, create_time createTime, update_time updateTime "
                + "from tally_userinfo where id = #{id}")
    UserInfoDo getUserInfoByUserId(@Param("id") long id);
}
