package com.lsz.depot.apply.base.dao;

import com.lsz.depot.apply.po.ProductTypeInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductTypeInfoDao extends JpaRepository<ProductTypeInfo, Long> {
    
    ProductTypeInfo findByUuid(String uuid);
    
    //名称 <required>
    ProductTypeInfo findByName(String name);
    
    //类型  select[类别,包含文字] default[类别]
    ProductTypeInfo findByType(String type);
    
    Page<ProductTypeInfo> findAll(Specification<ProductTypeInfo> specification, Pageable pageable);
    
    List<ProductTypeInfo> findAll();
    
    @Transactional
    void deleteByUuid(String uuid);
}
