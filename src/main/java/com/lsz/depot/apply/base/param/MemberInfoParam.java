package com.lsz.depot.apply.base.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoParam{
    
    //账号 <key><required>
    private String account;
    
    //名称 <required>
    private String name;
    
    //角色  select[管理员,操作员,来宾] default[来宾]
    private String role;
    
    //性别  radio[男,女] default[男]
    private String sex;
    
    //手机 
    private String phone;
    
}
