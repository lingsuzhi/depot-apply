package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.SuggestionInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionDao extends JpaRepository<SuggestionInfo, Long> {
    SuggestionInfo findByUuid(String uuid);


}