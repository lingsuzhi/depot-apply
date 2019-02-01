package com.lsz.depot.core.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 单据管理
 */
@Data
@NoArgsConstructor
public class OrderInfoDTO implements Serializable {

    //编号
    private String uuid;

    //颜色 <rowcolor>
    private String color;

    //备注 <textarea>
    private String remark;

    //客户
    private String customer;

    //制单人
    private String dutyPerson;

    //流水号(天)
    private Integer daySerial;

    //日期
    @JSONField(format = "yyyy-MM-dd")
    private LocalDateTime date;

    //流水号
    private String serialNumber;

    //折扣
    private Integer discount;

    //总数量
    private Double countAmount;

    //总金额
    private BigDecimal countTotal;

    //数据
    private String data;

    //扩展字段 json格式
    private String extendField;

    //状态
    private String status;

    //类别
    private String type;

}
