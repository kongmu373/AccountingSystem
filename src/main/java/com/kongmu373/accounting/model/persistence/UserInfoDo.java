package com.kongmu373.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDo {

    private long id;
    private String username;
    private String password;
    private LocalDate createTime;
    private LocalDate updateTime;
    private String salt;

}
