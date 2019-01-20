package com.lsz.depot.core.dao;


import com.lsz.depot.core.common.PageParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class DaoUtil {

    /**
     * 生成分页
     *
     * @param param
     * @return
     */
    public static Pageable getPageable(PageParam<?> param) {
        if (param.getSort() == null) {
            return PageRequest.of(param.getPageNum() - 1, param.getPageSize());
        }
        Sort pageSort = new Sort(
                (param.getOrder() == null || PageParam.PageOrderType.DESC.equals(param.getOrder())) ? Direction.DESC
                        : Direction.ASC,
                param.getSort());
        Pageable pageable = PageRequest.of(param.getPageNum() - 1, param.getPageSize(), pageSort);
        return pageable;
    }
}
