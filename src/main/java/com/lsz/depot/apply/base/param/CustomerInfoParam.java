package com.lsz.depot.apply.base.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfoParam{
    
    //编号 <key><required>
    private String number;
    
    //名称 <required>
    private String name;
    
    //电话 
    private String phone;
    
    //类别  select[客户,供应商,内部] default[客户]
    private String type;
    
}
