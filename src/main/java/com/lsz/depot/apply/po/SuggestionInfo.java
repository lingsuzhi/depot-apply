package com.lsz.depot.apply.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "suggestion_info")
public class SuggestionInfo extends BasePO{

     //标题
     private String title;

     //内容
     private String content;

     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private LocalDateTime date;
}
