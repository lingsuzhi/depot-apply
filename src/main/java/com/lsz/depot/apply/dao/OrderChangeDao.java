package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.OrderChangeInfo;
import com.lsz.depot.apply.po.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderChangeDao extends JpaRepository<OrderChangeInfo, Long> {

    List<OrderChangeInfo> findAllByStatusFalse();
}