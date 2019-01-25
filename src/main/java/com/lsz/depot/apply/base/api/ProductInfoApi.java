package com.lsz.depot.apply.base.api;

import com.lsz.depot.apply.base.dto.ProductInfoDto;
import com.lsz.depot.apply.base.param.ProductInfoParam;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.service.ProductInfoService;
import com.lsz.depot.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lsz on 2019-01-20
 * 商品资料
 */

@RestController
@RequestMapping(value = "/api/base/productInfo")
public class ProductInfoApi {

    @Autowired
    private ProductInfoService productInfoService;
    
    @RequestMapping(value = "/findByUuid")
    public ResponseInfo<ProductInfoDto> findByUuid(@RequestParam String uuid) {
        return ResponseInfo.success(productInfoService.findByUuid(uuid));
    }
    
    @RequestMapping(value = "/findPage")
    public ResponseInfo<Page<ProductInfoDto>> findAll(@RequestBody PageParam<ProductInfoParam> productInfoParam) {
        return ResponseInfo.success(productInfoService.findAll(productInfoParam));
    }
    
    @PostMapping(value = "/addNew")
    public ResponseInfo<String> addNew(@RequestBody ProductInfoDto productInfoDto) {
        return ResponseInfo.assertion(productInfoService.addNew(productInfoDto));
    }
    
    @PostMapping(value = "/update")
    public ResponseInfo<String> update(@RequestBody ProductInfoDto productInfoDto) {
        return ResponseInfo.assertion(productInfoService.update(productInfoDto));
    }
    
    @RequestMapping(value = "/delByUuid")
    public ResponseInfo<Boolean> delByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(productInfoService.delByUuid(uuid));
    }
    
}
