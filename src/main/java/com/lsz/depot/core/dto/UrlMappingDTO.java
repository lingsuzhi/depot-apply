package com.lsz.depot.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UrlMappingDTO {
    //名称
    private String name;
    //路径
    private String url;
    //描述
    private String desc;
    //缓存
    private Integer cache;
}
