package com.lsz.depot.apply.base.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductTypeInfoDto {
    
    //uuid
    private String uuid;
    
    //名称 <required>
    private String name;

    //上级id
    private String parentId;
    //上级
    private String parent;
    
    //类型  select[类别,包含文字] default[类别]
    private String type;
    
    //备注 <textarea>
    private String remark;

}
