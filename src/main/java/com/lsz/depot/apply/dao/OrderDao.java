package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderDao extends JpaRepository<OrderInfo, Long> {

    OrderInfo findFirstByTypeAndDateOrderByDaySerialDesc(String type, LocalDateTime date);

    OrderInfo findFirstByTypeOrderByDaySerialDesc(String type);

    OrderInfo findByTypeAndDateAndDaySerial(String type, LocalDateTime date, Integer daySerial);

    OrderInfo findByUuid(String uuid);

    OrderInfo findByNumber(String number);

    void deleteByUuid(String uuid);
}