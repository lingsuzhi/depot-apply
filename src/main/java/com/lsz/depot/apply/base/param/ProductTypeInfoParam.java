package com.lsz.depot.apply.base.param;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductTypeInfoParam{
    
    //名称 <required>
    private String name;
    
    //类型  select[类别,包含文字] default[类别]
    private String type;
    
}
