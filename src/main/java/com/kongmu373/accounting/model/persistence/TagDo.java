package com.kongmu373.accounting.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDo {
    private Long id;
    private String description;
    private Long userId;
    private Integer status; // 0 -> disable, 1 -> enable
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
