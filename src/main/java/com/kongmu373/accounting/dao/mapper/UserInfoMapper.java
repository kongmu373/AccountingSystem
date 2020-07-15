package com.kongmu373.accounting.dao.mapper;

import com.kongmu373.accounting.model.persistence.UserInfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {

    @Select("select id, username, password, create_time, update_time from tally_userinfo where id = #{id}")
    UserInfoDO getUserInfoByUserId(@Param("id")long id);
}
