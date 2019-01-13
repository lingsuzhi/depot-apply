package com.lsz.depot.apply.po;


import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@Data
@NoArgsConstructor
@Entity
public class UrlMapping extends BasePO{
     //名称
     private String name;
     //路径
     private String url;
     //描述
     private String desc;
     //缓存
     private Integer cache;
     //类型
     private String type;
}
