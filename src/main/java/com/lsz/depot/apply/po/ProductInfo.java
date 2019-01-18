package com.lsz.depot.apply.po;

import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;


/**
 * 商品资料
 */
@Data
@NoArgsConstructor
@Entity
public class ProductInfo extends BasePO {

    //编号 <key><param><required>
    private String number;
    //名称 <param><required>
    private String name;
    //规格 <param>
    private String spec;
    //含量
    private String content;
    //单位  select[箱,个,只] default[箱]
    private String company;
    //类别id   <隐藏>
    private String typeId;
    //类别 <param>
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
    //备注 <textarea>
    private String remark;
    //颜色 <rowcolor>
    private String color;
    //标记
    private String badge ;

}
