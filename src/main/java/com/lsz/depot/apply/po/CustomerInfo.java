package com.lsz.depot.apply.po;

import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;


/**
 * 客户资料
 */
@Data
@NoArgsConstructor
@Entity
public class CustomerInfo extends BasePO {

    //编号 <key><param><required>
    private String number;
    //名称 <param><required>
    private String name;
    //电话 <param>
    private String phone;
    //邮箱
    private String email;
    //类别 <param> select[客户,供应商,内部] default[客户]
    private String type;
    //排序
    private Double sort;
    //备注 <textarea>
    private String remark;
    //颜色 <rowcolor>
    private String color;

}
