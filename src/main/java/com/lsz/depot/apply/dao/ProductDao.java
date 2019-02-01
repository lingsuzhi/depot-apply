package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDao extends JpaRepository<ProductInfo, Long> {

//    @Query(value = "UPDATE product_info  SET add_count= ?1 where  number=?2",nativeQuery = true)
//    int updateAddCount( Double val,  String number);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set add_count = add_count + :val where number = :number", nativeQuery = true)
    int updateAddCount(@Param("number") String number, @Param("val") Double val);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set sub_count = sub_count + :val where number = :number", nativeQuery = true)
    int updateSubCount(@Param("number") String number, @Param("val") Double val);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set transfer_count = transfer_count + :val where number = :number", nativeQuery = true)
    int updateTransferCount(@Param("number") String number, @Param("val") Double val);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set add_count = add_count - :val where number = :number", nativeQuery = true)
    int subAddCount(@Param("number") String number, @Param("val") Double val);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set sub_count = sub_count - :val where number = :number", nativeQuery = true)
    int subSubCount(@Param("number") String number, @Param("val") Double val);

    @Modifying
    @Transactional
    @Query(value = "update `product_info` set transfer_count = transfer_count - :val where number = :number", nativeQuery = true)
    int subTransferCount(@Param("number") String number, @Param("val") Double val);
//    @Modifying
//    @Query(value = "update ProductInfo set addCount = 1 where number = :number")
//    int updateAddCount(@Param("number") String number);
}
