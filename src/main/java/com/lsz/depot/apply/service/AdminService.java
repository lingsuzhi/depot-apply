package com.lsz.depot.apply.service;


import com.lsz.depot.apply.base.dao.MemberInfoDao;
import com.lsz.depot.apply.dao.AdminDao;
import com.lsz.depot.apply.po.AdminInfo;
import com.lsz.core.dto.AdminDTO;
import com.lsz.core.dto.AdminHomeDTO;
import com.lsz.core.dto.LoginDTO;
import com.lsz.core.dto.MiniAdminDTO;
import com.lsz.core.utils.Base64Util;
import com.lsz.core.utils.BeanUtil;
import com.lsz.core.utils.TokenUtil;
import com.lsz.depot.apply.po.MemberInfo;
import com.lsz.depot.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


@Slf4j
@Service
public class AdminService {

    @Autowired
    private MemberInfoDao memberInfoDao;

     public AdminHomeDTO getCurrentUser() {
        String currentUserId = TokenUtil.getCurrentUserId();
        if (StringUtils.isEmpty(currentUserId)) {
            return null;
        }
         MemberInfo memberInfo = memberInfoDao.findByAccount(currentUserId);
        if (memberInfo == null){
            return null;
        }
        AdminHomeDTO adminHomeDTO = new AdminHomeDTO();
        adminHomeDTO.setName(memberInfo.getName());
        adminHomeDTO.setUserId(memberInfo.getAccount());
        adminHomeDTO.setAdmin(true);
        return adminHomeDTO;
    }

    public MiniAdminDTO login(LoginDTO loginDTO) {
        log.info("loginDTO {}", loginDTO);
        MemberInfo memberInfo = memberInfoDao.findByAccount(loginDTO.getName());
        if (memberInfo == null) {
            throw new BusinessException("3338", "登录失败，用户不存在！");
        }
        String pwd = memberInfo.getPassword();
        if ("base64".equals(memberInfo.getPwdType())) {
            pwd = Base64Util.encryptBASE64(pwd);
        }
        if (!loginDTO.getPwd().equals(pwd)) {
            throw new BusinessException("3349", "登录失败，密码错误！");
        }
        MiniAdminDTO miniAdminDTO = new MiniAdminDTO();
        miniAdminDTO.setToken(TokenUtil.createToken(memberInfo.getAccount()));
        miniAdminDTO.setUserId(memberInfo.getAccount());
        miniAdminDTO.setNikeName(memberInfo.getName());
        miniAdminDTO.setLoginTime(LocalDateTime.now());
        //暂时不做登录日志
        return miniAdminDTO;
    }


}