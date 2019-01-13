package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UrlMappingDao extends JpaRepository<UrlMapping, Long> {
    List<UrlMapping> findAll();

}