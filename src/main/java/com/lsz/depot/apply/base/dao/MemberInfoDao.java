package com.lsz.depot.apply.base.dao;

import com.lsz.depot.apply.po.MemberInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface MemberInfoDao extends JpaRepository<MemberInfo, Long> {
    
    MemberInfo findByUuid(String uuid);
    
    //账号 <key><required>
    MemberInfo findByAccount(String account);
    
    //名称 <required>
    MemberInfo findByName(String name);
    
    //角色  select[管理员,操作员,来宾] default[来宾]
    MemberInfo findByRole(String role);
    
    //性别  radio[男,女] default[男]
    MemberInfo findBySex(String sex);
    
    //手机 
    MemberInfo findByPhone(String phone);
    
    Page<MemberInfo> findAll(Specification<MemberInfo> specification, Pageable pageable);
    
    List<MemberInfo> findAll();
    
    @Transactional
    void deleteByUuid(String uuid);
}
