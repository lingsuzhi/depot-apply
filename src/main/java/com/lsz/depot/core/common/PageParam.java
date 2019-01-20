package com.lsz.depot.core.common;


import lombok.Data;


/**
 * 分页参数
 *
 */
@Data
public class PageParam<T> {

    //当前页码
    private int pageSize;

    //每页数，最大100
    private int pageNum;

    private PageOrderType order;

    private String sort;

    private T query;

    public enum PageOrderType {

        /**
         * 升序
         */
        ASC,

        /**
         * 降序
         */
        DESC
    }
}
