package com.lsz.depot.apply.service;

import com.lsz.depot.apply.dao.UrlMappingDao;
import com.lsz.core.dto.UrlMappingDTO;
import com.lsz.depot.apply.po.UrlMapping;
import com.lsz.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UrlMappingService {

    @Autowired
    private UrlMappingDao urlMappingDao;

    public List<UrlMappingDTO> findAllUrl() {
        List<UrlMapping> urlMappings = urlMappingDao.findAll();
        if (CollectionUtils.isEmpty(urlMappings)) {
            log.info("查询url结果为空。");
            return null;
        }
        List<UrlMappingDTO> list = new ArrayList<>();
        for (UrlMapping urlMapping : urlMappings) {
            list.add(BeanUtil.copyBean(urlMapping, UrlMappingDTO.class));
        }
        return list;
    }
}
