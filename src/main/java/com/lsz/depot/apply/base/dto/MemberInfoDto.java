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
    
    //账号 <key><required>
    private String account;
    
    //名称 <required>
    private String name;
    
    //角色  select[管理员,来宾] default['来宾']
    private String role;
    
    //性别  radio[男,女] default['男']
    private String sex;
    
    //手机 
    private String phone;
    
    //注册时间 <隐藏编辑><时间>
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerDate;
    
    //谷歌验证 <隐藏编辑>
    private String googleKey;
    
    //备注 <textarea>
    private String remark;

}
