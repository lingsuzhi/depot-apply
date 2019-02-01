package com.lsz.depot.apply.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductInfoVO {
    //编号
    private String number;

    //名称
    private String name;

    //规格
    private String spec;

    //含量
    private String content;

    //单位
    private String company;

    //单价
    private BigDecimal proce;

    //数量
    private Double count;

    //金额
    private BigDecimal total;
}
