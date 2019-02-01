package com.lsz.depot.apply.po;

import com.lsz.depot.framework.po.BaseNoInfoPO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.math.BigDecimal;


/**
 * 单据管理
 */
@Data
@NoArgsConstructor
@Entity
public class OrderChangeInfo extends BaseNoInfoPO {

    //编号
    private String uuid;

    //数据
    private String data;

    //状态 为true 已经处理过的
    private Boolean status;

    //操作
    private String operate;

    //总金额
    private BigDecimal countTotal;

    //是否增加
    private Boolean isAdd;

}
