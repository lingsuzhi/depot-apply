package com.lsz.depot.core.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 简单管理员信息
 */
@Data
@NoArgsConstructor
public class MiniAdminDTO implements Serializable {

    //账号
    private String userId;

    private String token;
    //登录时间
    @JSONField(format  = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    private String nikeName;

}
