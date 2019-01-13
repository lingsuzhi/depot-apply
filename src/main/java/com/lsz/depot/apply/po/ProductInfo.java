package com.lsz.depot.apply.po;

import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;


/**
 * 商品表
 */
@Data
@NoArgsConstructor
@Entity
public class ProductInfo extends BasePO {

    // key 编号
    private String number;
    // --名称
    private String name;
    // --规格
    private String spec;
    // 条形码
    private String barcode;
    // 生产厂家
    private String manufacturer;
    // 含量
    private String content;
    // 描述
    private String description;
    // 图片
    private String image;
    // 成本价
    private BigDecimal cost;
    // 价格
    private BigDecimal proce;
    // --类别
    private String typeId;
    // 单位
    private String company;
    // 等级
    private Integer level;
    // 初始库存
    private BigDecimal stock;
    // 状态
    private Boolean status;
}
