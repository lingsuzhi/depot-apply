package com.lsz.depot.apply.base.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfoDto {
    
    //uuid
    private String uuid;
    
    //编号
    private String number;
    
    //名称
    private String name;
    
    //电话
    private String phone;
    
    //邮箱
    private String email;
    
    //类别
    private String type;
    
    //排序
    private Double sort;
    
    //备注
    private String remark;
    
    //颜色
    private String color;
    //创建时间
    private LocalDateTime createDate;

}
