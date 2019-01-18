package com.lsz.depot.apply.po;


import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 用户信息
 */
@Data
@NoArgsConstructor
@Entity
public class MemberInfo extends BasePO {

    //账号 <key><param><required>
    private String account;
    //名称 <param><required>
    private String name;
    //角色 <param> select[管理员,操作员,来宾] default[来宾]
    private String role;
    //性别 <param> radio[男,女] default[男]
    private String sex;
    //手机 <param>
    private String phone;
    //密码  <隐藏>
    private String password;
    //加密类型 <隐藏>
    private String pwdType;
    //注册时间 <隐藏编辑><时间>
    private LocalDateTime registerDate;
    //谷歌验证 <隐藏编辑>
    private String googleKey;
    //备注 <textarea>
    private String remark;
}
