package com.lsz.depot.apply.po;


import com.lsz.depot.framework.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@Entity
public class MenuInfo extends BasePO{

     private String menuId;
     /** 功能名称 */
     private String menuName;
     /** 功能地址 */
     private String menuUrl;
     /** 父菜单ID */
     private String parentId;
     /** 类型：1目录，2菜单，3按钮 */
     private String type;
     /** 图标样式 */
     private String icon;
     /** 排序 */
     private Integer sort;
     /** 状态：0无效，1有效 */
     private Boolean status;
     /** 备注 */
     private String remark;

}
