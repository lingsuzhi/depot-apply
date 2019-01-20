package com.lsz.depot.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminDTO {

    private String uuid;
    //性别
    private Integer sex;
    //用户昵称
    private String nikeName;
    //手机号码
    private String cellPhone;

    public AdminDTO(String uuid, Integer sex, String nikeName, String cellPhone) {
        this.uuid = uuid;
        this.sex = sex;
        this.nikeName = nikeName;
        this.cellPhone = cellPhone;
    }
}
