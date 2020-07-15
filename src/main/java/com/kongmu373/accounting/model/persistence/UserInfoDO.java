package com.kongmu373.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDO {

    private long id;
    private String username;
    private String password;
    private Instant create_time;
    private Instant update_time;

}
