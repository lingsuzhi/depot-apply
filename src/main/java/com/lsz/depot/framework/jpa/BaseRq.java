package com.lsz.depot.framework.jpa;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
public class BaseRq implements java.io.Serializable {

    private Integer page = 1;
    private Integer limit = 15;
    //做了个 兼容字段
    private Integer pageNum;
    private Integer pageSize;

    private Sort.Direction direction = Sort.Direction.ASC;
    private String propertie;

    public Integer getPageEx() {
        if (pageNum != null) {
            return pageNum;
        }
        return page - 1;
    }

    public Integer getPageSizeEx() {
        if (pageSize != null) {
            return pageSize;
        }
        return limit;
    }

    public PageRequest getPageRequest() {
        if (StringUtils.isEmpty(propertie)) {
            return PageRequest.of(getPageEx(), getPageSizeEx());
        } else {
            return PageRequest.of(getPageEx(), getPageSizeEx(), new Sort(direction, propertie));
        }

    }

}
