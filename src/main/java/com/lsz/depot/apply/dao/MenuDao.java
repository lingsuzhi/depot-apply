package com.lsz.depot.apply.dao;

import com.lsz.depot.apply.po.MenuInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MenuDao extends JpaRepository<MenuInfo, Long> {
    MenuInfo findByMenuId(String menuId);

    List<MenuInfo> findByStatusTrue();


}