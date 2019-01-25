package com.lsz.depot.apply.base.api;

import com.lsz.depot.apply.base.dto.ProductTypeInfoDto;
import com.lsz.depot.apply.base.param.ProductTypeInfoParam;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.service.ProductTypeInfoService;
import com.lsz.depot.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lsz on 2019-01-15
 * 商品分类
 */

@RestController
@RequestMapping(value = "/api/base/productTypeInfo")
public class ProductTypeInfoApi {

    @Autowired
    private ProductTypeInfoService productTypeInfoService;
    
    @RequestMapping(value = "/findByUuid")
    public ResponseInfo<ProductTypeInfoDto> findByUuid(@RequestParam String uuid) {
        return ResponseInfo.success(productTypeInfoService.findByUuid(uuid));
    }
    
    @RequestMapping(value = "/findPage")
    public ResponseInfo<Page<ProductTypeInfoDto>> findAll(@RequestBody PageParam<ProductTypeInfoParam> productTypeInfoParam) {
        return ResponseInfo.success(productTypeInfoService.findAll(productTypeInfoParam));
    }
    
    @PostMapping(value = "/addNew")
    public ResponseInfo<String> addNew(@RequestBody ProductTypeInfoDto productTypeInfoDto) {
        return ResponseInfo.assertion(productTypeInfoService.addNew(productTypeInfoDto));
    }
    
    @PostMapping(value = "/update")
    public ResponseInfo<String> update(@RequestBody ProductTypeInfoDto productTypeInfoDto) {
        return ResponseInfo.assertion(productTypeInfoService.update(productTypeInfoDto));
    }
    
    @RequestMapping(value = "/delByUuid")
    public ResponseInfo<Boolean> delByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(productTypeInfoService.delByUuid(uuid));
    }
    
}
