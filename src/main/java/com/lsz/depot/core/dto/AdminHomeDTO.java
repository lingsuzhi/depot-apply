package com.lsz.depot.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminHomeDTO {

    private String name;
    private String userId;
    private Boolean admin ;
}
