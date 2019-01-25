package com.lsz.depot.apply.base.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInfoParam{
    
    //编号 <key><required>
    private String number;
    
    //名称 <required>
    private String name;
    
    //规格 
    private String spec;
    
    //类别 
    private String type;
    
}
