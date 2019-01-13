package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminDao extends JpaRepository<AdminInfo, Long> {

    AdminInfo findByUserId(String userId);

}