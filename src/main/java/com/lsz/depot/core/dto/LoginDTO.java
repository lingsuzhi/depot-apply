package com.lsz.depot.core.dto;

import lombok.Data;


/**
 * 登录
 */
@Data
public class LoginDTO {

    //账号
    private String name;
    //密码
    private String pwd;
}
