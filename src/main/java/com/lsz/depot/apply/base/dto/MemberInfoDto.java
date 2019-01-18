package com.lsz.depot.apply.base.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {
    
    //uuid
    private String uuid;
    
    //账号
    private String account;
    
    //名称
    private String name;
    
    //角色
    private String role;
    
    //性别
    private String sex;
    
    //手机
    private String phone;
    
    //注册时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;
    
    //谷歌验证
    private String googleKey;
    
    //备注
    private String remark;

}
