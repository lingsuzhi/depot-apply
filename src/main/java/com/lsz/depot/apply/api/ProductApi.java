package com.lsz.depot.apply.api;

import com.lsz.depot.core.common.ResponseInfo;
import com.lsz.depot.core.dto.UrlMappingDTO;
import com.lsz.depot.apply.service.ProductService;
import com.lsz.depot.apply.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 初始化
 */
@RestController
@RequestMapping(value = "/api/product")
public class ProductApi {

    @Autowired
    private ProductService service;


    /**
     * 所有接口
     * @return
     */
    @RequestMapping(value = "/findMaxNumber")
    public ResponseInfo<String> findMaxNumber(String type) {
        return ResponseInfo.assertion(service.findMaxNumber(type));
    }

}
