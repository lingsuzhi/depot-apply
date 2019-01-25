package com.lsz.depot.apply.base.api;

import com.lsz.depot.apply.base.dto.CustomerInfoDto;
import com.lsz.depot.apply.base.param.CustomerInfoParam;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.service.CustomerInfoService;
import com.lsz.depot.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lsz on 2019-01-24
 * 客户资料
 */

@RestController
@RequestMapping(value = "/api/base/customerInfo")
public class CustomerInfoApi {

    @Autowired
    private CustomerInfoService customerInfoService;
    
    @RequestMapping(value = "/findByUuid")
    public ResponseInfo<CustomerInfoDto> findByUuid(@RequestParam String uuid) {
        return ResponseInfo.success(customerInfoService.findByUuid(uuid));
    }
    
    @RequestMapping(value = "/findPage")
    public ResponseInfo<Page<CustomerInfoDto>> findAll(@RequestBody PageParam<CustomerInfoParam> customerInfoParam) {
        return ResponseInfo.success(customerInfoService.findAll(customerInfoParam));
    }
    
    @PostMapping(value = "/addNew")
    public ResponseInfo<String> addNew(@RequestBody CustomerInfoDto customerInfoDto) {
        return ResponseInfo.assertion(customerInfoService.addNew(customerInfoDto));
    }
    
    @PostMapping(value = "/update")
    public ResponseInfo<String> update(@RequestBody CustomerInfoDto customerInfoDto) {
        return ResponseInfo.assertion(customerInfoService.update(customerInfoDto));
    }
    
    @RequestMapping(value = "/delByUuid")
    public ResponseInfo<Boolean> delByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(customerInfoService.delByUuid(uuid));
    }
    
}
