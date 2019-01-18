package com.lsz.depot.apply.base.service;

import com.lsz.depot.apply.po.MemberInfo;
import com.lsz.core.common.PageParam;
import com.lsz.depot.apply.base.param.MemberInfoParam;
import com.lsz.core.dao.DaoUtil;
import com.lsz.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import com.lsz.core.utils.UuidMd5;
import com.lsz.depot.apply.base.dao.MemberInfoDao;
import org.springframework.data.domain.Pageable;
import com.lsz.depot.apply.base.dto.MemberInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import org.springframework.util.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MemberInfoService {

    @Autowired
    private MemberInfoDao memberInfoDao;
    
    public MemberInfoDto findByUuid(String uuid) {
        MemberInfo memberInfo = memberInfoDao.findByUuid(uuid);
        return BeanUtil.copyBean(memberInfo, MemberInfoDto.class);
    }
    
    public String addNew(MemberInfoDto memberInfoDto) {
        MemberInfo memberInfo = BeanUtil.copyBean(memberInfoDto, MemberInfo.class);
        String uuid = UuidMd5.uuidWith22Bit();
        log.info("addNew memberInfo：{}", memberInfoDto);
        
        memberInfo.setUuid(uuid);
        memberInfoDao.save(memberInfo);
        return uuid;
    }
    
    public Boolean delByUuid(String uuid) {
        memberInfoDao.deleteByUuid(uuid);
        return true;
    }
    
    public String update(MemberInfoDto memberInfoDto) {
        String uuid = memberInfoDto.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        MemberInfo memberInfo = memberInfoDao.findByUuid(uuid);
        if (memberInfo == null) {
            log.info("update数据不存在，请查证uuid {}", uuid);
            return null;
        }
        MemberInfo info = BeanUtil.copyBeanNotNull(memberInfoDto, memberInfo);
        memberInfoDao.save(info);
        return uuid;
    }
    
    private Page pageToDto(Page<MemberInfo> memberInfoPage, Pageable pageable) {
        if (memberInfoPage == null || CollectionUtils.isEmpty(memberInfoPage.getContent())) {
            return memberInfoPage;
        }
        List<MemberInfo> content = memberInfoPage.getContent();
        List<MemberInfoDto> list = new ArrayList<>();
        for (MemberInfo memberInfo : content) {
            MemberInfoDto memberInfoDto = BeanUtil.copyBean(memberInfo, MemberInfoDto.class);
            list.add(memberInfoDto);
        }
        return new PageImpl<>(list, pageable, memberInfoPage.getTotalElements());
    }
    
    public Page<MemberInfoDto> findAll(PageParam<MemberInfoParam> memberInfoParam) {
        Pageable pageable = DaoUtil.getPageable(memberInfoParam);
        Specification<MemberInfo> specification = (Specification<MemberInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //账号 <key><required>
            if (!StringUtils.isEmpty(memberInfoParam.getQuery().getAccount())) {
                Predicate predicate = criteriaBuilder.like(root.get("account").as(String.class), "%" + memberInfoParam.getQuery().getAccount() + "%");
                predicates.add(predicate);
            }
            
            //名称 <required>
            if (!StringUtils.isEmpty(memberInfoParam.getQuery().getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + memberInfoParam.getQuery().getName() + "%");
                predicates.add(predicate);
            }
            
            //角色  select[管理员,操作员,来宾] default[来宾]
            if (!StringUtils.isEmpty(memberInfoParam.getQuery().getRole())) {
                Predicate predicate = criteriaBuilder.like(root.get("role").as(String.class), "%" + memberInfoParam.getQuery().getRole() + "%");
                predicates.add(predicate);
            }
            
            //性别  radio[男,女] default[男]
            if (!StringUtils.isEmpty(memberInfoParam.getQuery().getSex())) {
                Predicate predicate = criteriaBuilder.like(root.get("sex").as(String.class), "%" + memberInfoParam.getQuery().getSex() + "%");
                predicates.add(predicate);
            }
            
            //手机 
            if (!StringUtils.isEmpty(memberInfoParam.getQuery().getPhone())) {
                Predicate predicate = criteriaBuilder.like(root.get("phone").as(String.class), "%" + memberInfoParam.getQuery().getPhone() + "%");
                predicates.add(predicate);
            }
            
            if (predicates.size() == 0) {
                return null;
            }
            Predicate[] predicateArr = new Predicate[predicates.size()];
            predicateArr = predicates.toArray(predicateArr);
            return criteriaBuilder.and(predicateArr);
        };
        Page<MemberInfo> memberInfoPage = memberInfoDao.findAll(specification, pageable);
        return pageToDto(memberInfoPage, pageable);
    }

}
