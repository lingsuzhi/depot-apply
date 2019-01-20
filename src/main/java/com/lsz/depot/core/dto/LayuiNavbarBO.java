package com.lsz.depot.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ex-lingsuzhi on 2018/4/17.
 */
@Data
public class LayuiNavbarBO {
    private String id;
    private String title;
    private String icon;
    private Boolean spread;   //是否父节点
    private List<LayuiNavbarBO> children = new ArrayList<>();
    private String url;
    private Integer sort;

}
