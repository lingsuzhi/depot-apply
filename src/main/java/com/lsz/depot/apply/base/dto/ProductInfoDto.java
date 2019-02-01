package com.lsz.depot.apply.base.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInfoDto {
    
    //uuid
    private String uuid;
    
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
    
    //类别
    private String type;
    
    //条形码
    private String barcode;
    
    //生产厂家
    private String manufacturer;
    
    //图片
    private String image;
    
    //成本价
    private BigDecimal cost;
    
    //售价
    private BigDecimal proce;
    
    //状态
    private Boolean status;
    
    //排序
    private Double sort;
    
    //备注
    private String remark;
    
    //颜色
    private String color;
    
    //标记
    private String badge;
//
//    //初始库存
//    private Double initStock;
//
//    //增加数量
//    private Double addCount;
//
//    //减少数量
//    private Double subCount;
//
//    //调整数量
//    private Double transferCount;

    //其他金额
    private BigDecimal otherProce;
}
