package com.lsz.depot.apply.service;

import com.lsz.depot.apply.dao.SuggestionDao;
import com.lsz.depot.apply.po.SuggestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TestService {

    @Autowired
    private SuggestionDao dao;

    public SuggestionInfo save(SuggestionInfo entity) {
        return dao.save(entity);
    }

    @Transactional
    public void test(SuggestionInfo entity) throws Exception {
        //不会回滚
        this.save(entity);
        throw new Exception("sysconfig error");

    }

    @Transactional
    public String test1(SuggestionInfo entity)  {
        //会回滚
        SuggestionInfo suggestionInfo = new SuggestionInfo();
        suggestionInfo.setTitle("title");
        suggestionInfo.setUuid("uuid");
        dao.save(suggestionInfo);

        int i = 1/0;
        return "1";
    }

    @Transactional
    public void test2(SuggestionInfo entity) throws Exception {
        //会回滚
        this.save(entity);
        throw new RuntimeException("sysconfig error");

    }

    public void test3(SuggestionInfo entity) throws Exception {
        //事务仍然会被提交
        this.test4(entity);
    }

    @Transactional(rollbackFor = Exception.class,propagation=Propagation.REQUIRES_NEW)
    public void test4(SuggestionInfo entity) throws Exception {

        this.save(entity);
        throw new Exception("sysconfig error");
    }



}