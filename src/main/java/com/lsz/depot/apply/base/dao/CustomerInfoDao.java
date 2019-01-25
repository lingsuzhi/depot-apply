package com.lsz.depot.apply.base.dao;

import com.lsz.depot.apply.po.CustomerInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CustomerInfoDao extends JpaRepository<CustomerInfo, Long> {
    
    CustomerInfo findByUuid(String uuid);
    
    //编号
    CustomerInfo findByNumber(String number);
    
    //名称
    List<CustomerInfo> findByName(String name);
    
    //电话
    List<CustomerInfo> findByPhone(String phone);
    
    //类别
    List<CustomerInfo> findByType(String type);
    
    Page<CustomerInfo> findAll(Specification<CustomerInfo> specification, Pageable pageable);
    
    List<CustomerInfo> findAll();
    
    @Transactional
    void deleteByUuid(String uuid);
}
