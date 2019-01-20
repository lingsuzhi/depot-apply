package com.lsz.depot.apply.base.api;

import com.lsz.depot.apply.base.dto.MemberInfoDto;
import com.lsz.depot.apply.base.param.MemberInfoParam;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.service.MemberInfoService;
import com.lsz.depot.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lsz on 2019-01-20
 * 用户信息
 */

@RestController
@RequestMapping(value = "/api/base/memberInfo")
public class MemberInfoApi {

    @Autowired
    private MemberInfoService memberInfoService;
    
    @RequestMapping(value = "/findByUuid")
    public ResponseInfo<MemberInfoDto> findByUuid(@RequestParam String uuid) {
        return ResponseInfo.success(memberInfoService.findByUuid(uuid));
    }
    
    @RequestMapping(value = "/findPage")
    public ResponseInfo<Page<MemberInfoDto>> findAll(@RequestBody PageParam<MemberInfoParam> memberInfoParam) {
        return ResponseInfo.success(memberInfoService.findAll(memberInfoParam));
    }
    
    @PostMapping(value = "/addNew")
    public ResponseInfo<String> addNew(@RequestBody MemberInfoDto memberInfoDto) {
        return ResponseInfo.assertion(memberInfoService.addNew(memberInfoDto));
    }
    
    @PostMapping(value = "/update")
    public ResponseInfo<String> update(@RequestBody MemberInfoDto memberInfoDto) {
        return ResponseInfo.assertion(memberInfoService.update(memberInfoDto));
    }
    
    @RequestMapping(value = "/delByUuid")
    public ResponseInfo<Boolean> delByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(memberInfoService.delByUuid(uuid));
    }
    
}
