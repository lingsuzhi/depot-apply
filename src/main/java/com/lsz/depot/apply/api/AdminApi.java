package com.lsz.depot.apply.api;


import com.lsz.depot.apply.service.AdminService;
import com.lsz.depot.apply.service.MenuService;
import com.lsz.core.contract.AdminContract;
import com.lsz.core.dto.AdminHomeDTO;
import com.lsz.core.dto.LayuiNavbarBO;
import com.lsz.core.dto.LoginDTO;
import com.lsz.core.dto.MiniAdminDTO;
import com.lsz.core.common.ResponseInfo;
import com.lsz.depot.framework.annotation.AspectExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/api/admin")
public class AdminApi implements AdminContract {

    @Autowired
    private AdminService adminService;
    @Autowired
    private MenuService menuService;


    @AspectExt(auth = false)
    @RequestMapping(value = "/login")
    public ResponseInfo<MiniAdminDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseInfo.assertion(adminService.login(loginDTO));
    }

    @Override
    @RequestMapping(value = "/menuTree")
    public ResponseInfo<List<LayuiNavbarBO>> menuTree(HttpServletRequest request) {
        return ResponseInfo.assertion(menuService.menuTree());
    }

    @RequestMapping(value = "/getCurrentUser")
    public ResponseInfo<AdminHomeDTO> getCurrentUser() {
        return ResponseInfo.assertion(adminService.getCurrentUser());
    }

}
