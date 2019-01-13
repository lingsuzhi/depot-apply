package com.lsz.depot.apply.po;


import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
//@Table(name = "admin_info")
public class AdminInfo extends BasePO{

     //账号
     private String userId;
     //性别
     private Integer sex;
     //用户昵称
     private String nikeName;
     //手机号码
     private String cellPhone;
     //密码
     private String password;
     //加密类型
     private String pwdType;
     //创建时间
     private LocalDateTime date;

}
