package com.lsz.depot.apply.api;

import com.lsz.core.dto.UrlMappingDTO;
import com.lsz.depot.apply.service.UrlMappingService;
import com.lsz.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 初始化
 */
@RestController
@RequestMapping(value = "/api/init")
public class InitApi {

    @Autowired
    private UrlMappingService urlMappingService;


    /**
     * 所有接口
     * @return
     */
    @RequestMapping(value = "/findAllUrl")
    public ResponseInfo<List<UrlMappingDTO>> findAllUrl() {
        return ResponseInfo.assertion(urlMappingService.findAllUrl());
    }

}
