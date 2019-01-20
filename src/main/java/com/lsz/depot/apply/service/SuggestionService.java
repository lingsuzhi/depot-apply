package com.lsz.depot.apply.service;

import com.lsz.depot.apply.dao.SuggestionDao;
import com.lsz.depot.core.dto.SuggestionDTO;
import com.lsz.depot.apply.po.SuggestionInfo;
import com.lsz.depot.core.utils.BeanUtil;
import com.lsz.depot.core.utils.UuidMd5;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class SuggestionService {


    @Autowired
    private SuggestionDao suggestionDao;

    public SuggestionDTO createSuggestion(SuggestionDTO suggestionDTO) {
        SuggestionInfo suggestionInfo = new SuggestionInfo();
        suggestionInfo.setContent(suggestionDTO.getContent());
        suggestionInfo.setTitle(suggestionDTO.getTitle());
        suggestionInfo.setUuid(UuidMd5.uuidWith22Bit());
        suggestionDao.save(suggestionInfo);

        return BeanUtil.copyBean(suggestionInfo, SuggestionDTO.class);
    }

    public SuggestionDTO updateSuggestion(SuggestionDTO suggestionDTO, String uuid) {

        SuggestionInfo suggestionInfo = suggestionDao.findByUuid(uuid);
        if (suggestionInfo == null) {
            log.info("修改失败，请查询id {}", uuid);
        }
        suggestionInfo.setTitle(suggestionDTO.getTitle());
        suggestionInfo.setContent(suggestionDTO.getContent());
        updateSuggestionDao(suggestionInfo);
        return BeanUtil.copyBean(suggestionInfo, SuggestionDTO.class);
    }

    @Transactional
    public Boolean updateSuggestionDao(SuggestionInfo suggestionInfo) {
        suggestionDao.save(suggestionInfo);
        return true;
    }

    public SuggestionDTO findBySuggestionUuid(String uuid) {
//        SuggestionInfo suggestionInfo = suggestionDao.findByUuid(uuid);
        SuggestionInfo suggestionInfo2 =suggestionDao.findByUuid(uuid);
        System.out.println(suggestionInfo2);
        return BeanUtil.copyBean(suggestionInfo2, SuggestionDTO.class);
    }

}
