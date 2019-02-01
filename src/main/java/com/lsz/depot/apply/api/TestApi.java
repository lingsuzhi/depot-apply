package com.lsz.depot.apply.api;

import com.lsz.depot.apply.po.SuggestionInfo;
import com.lsz.depot.apply.service.TestService;
import com.lsz.depot.core.common.ResponseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestApi {

    @Autowired
    private TestService service;

    @RequestMapping(value = "/test1")
    public ResponseInfo<String> test1() throws Exception {
        SuggestionInfo suggestionInfo = new SuggestionInfo();
        suggestionInfo.setTitle("title");
        suggestionInfo.setUuid("uuid");
        service.test3(suggestionInfo);
//        service.test(suggestionInfo);
        return ResponseInfo.assertion("");
    }
}
