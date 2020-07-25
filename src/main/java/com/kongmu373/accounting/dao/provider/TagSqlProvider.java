package com.kongmu373.accounting.dao.provider;

import com.kongmu373.accounting.model.persistence.TagDo;

import org.apache.ibatis.jdbc.SQL;

public class TagSqlProvider {

    /**
     * Update tag item via specific tag.
     *
     * @param tag the tag item need to update.
     * @return the sql to execute.
     */
    public String updateTag(TagDo tag) {
        return new SQL() {
            {
                UPDATE("tally_tag");
                if (tag.getDescription() != null) {
                    SET("description = #{description}");
                }
                if (tag.getStatus() != null) {
                    SET("status = #{status}");
                }
                WHERE("id=#{id}", "user_id=#{userId}");
            }
        }.toString();
    }
}
