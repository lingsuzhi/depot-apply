package com.lsz.depot.core.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SuggestionDTO {

    private String uuid;
    //标题
    private String title;
    //内容
    private String content;

    @JSONField(format = "yyyy-MM-dd HH:mm")
    private LocalDateTime date;

}
