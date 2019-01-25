package com.lsz.depot.apply.base.dao;

import com.lsz.depot.apply.po.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo, Long> {
    
    ProductInfo findByUuid(String uuid);
    
    //编号 <key><required>
    List<ProductInfo> findByNumber(String number);
    
    //名称 <required>
    List<ProductInfo> findByName(String name);
    
    //规格 
    List<ProductInfo> findBySpec(String spec);
    
    //类别 
    List<ProductInfo> findByType(String type);
    
    Page<ProductInfo> findAll(Specification<ProductInfo> specification, Pageable pageable);
    
    List<ProductInfo> findAll();
    
    @Transactional
    void deleteByUuid(String uuid);
}
