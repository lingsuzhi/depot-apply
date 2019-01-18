package com.lsz.depot.apply.po;


import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * 商品分类
 */
@Data
@NoArgsConstructor
@Entity
public class ProductTypeInfo extends BasePO {


    //名称 <param><required>
    private String name;

    //上级id <隐藏>
    private String parentId;

    //上级
    private String parent;

    //类型 <param> select[类别,包含文字] default[类别]
    private String type;

    //备注 <textarea>
    private String remark;
}
