package com.lsz.depot.apply.base.service;

import com.lsz.depot.apply.po.CustomerInfo;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.param.CustomerInfoParam;
import com.lsz.depot.core.dao.DaoUtil;
import com.lsz.depot.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import com.lsz.depot.core.utils.UuidMd5;
import com.lsz.depot.apply.base.dao.CustomerInfoDao;
import org.springframework.data.domain.Pageable;
import com.lsz.depot.apply.base.dto.CustomerInfoDto;
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
public class CustomerInfoService {

    @Autowired
    private CustomerInfoDao customerInfoDao;
    
    public CustomerInfoDto findByUuid(String uuid) {
        CustomerInfo customerInfo = customerInfoDao.findByUuid(uuid);
        return BeanUtil.copyBean(customerInfo, CustomerInfoDto.class);
    }
    
    public String addNew(CustomerInfoDto customerInfoDto) {
        CustomerInfo customerInfo = BeanUtil.copyBean(customerInfoDto, CustomerInfo.class);
        String uuid = UuidMd5.uuidWith22Bit();
        log.info("addNew customerInfo：{}", customerInfoDto);
        
        customerInfo.setUuid(uuid);
        customerInfoDao.save(customerInfo);
        return uuid;
    }
    
    public Boolean delByUuid(String uuid) {
        customerInfoDao.deleteByUuid(uuid);
        return true;
    }
    
    public String update(CustomerInfoDto customerInfoDto) {
        String uuid = customerInfoDto.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        CustomerInfo customerInfo = customerInfoDao.findByUuid(uuid);
        if (customerInfo == null) {
            log.info("update数据不存在，请查证uuid {}", uuid);
            return null;
        }
        CustomerInfo info = BeanUtil.copyBeanNotNull(customerInfoDto, customerInfo);
        customerInfoDao.save(info);
        return uuid;
    }
    
    private Page pageToDto(Page<CustomerInfo> customerInfoPage, Pageable pageable) {
        if (customerInfoPage == null || CollectionUtils.isEmpty(customerInfoPage.getContent())) {
            return customerInfoPage;
        }
        List<CustomerInfo> content = customerInfoPage.getContent();
        List<CustomerInfoDto> list = new ArrayList<>();
        for (CustomerInfo customerInfo : content) {
            CustomerInfoDto customerInfoDto = BeanUtil.copyBean(customerInfo, CustomerInfoDto.class);
            list.add(customerInfoDto);
        }
        return new PageImpl<>(list, pageable, customerInfoPage.getTotalElements());
    }
    
    public Page<CustomerInfoDto> findAll(PageParam<CustomerInfoParam> customerInfoParam) {
        Pageable pageable = DaoUtil.getPageable(customerInfoParam);
        Specification<CustomerInfo> specification = (Specification<CustomerInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //编号 <key><required>
            if (!StringUtils.isEmpty(customerInfoParam.getQuery().getNumber())) {
                Predicate predicate = criteriaBuilder.like(root.get("number").as(String.class), "%" + customerInfoParam.getQuery().getNumber() + "%");
                predicates.add(predicate);
            }
            
            //名称 <required>
            if (!StringUtils.isEmpty(customerInfoParam.getQuery().getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + customerInfoParam.getQuery().getName() + "%");
                predicates.add(predicate);
            }
            
            //电话 
            if (!StringUtils.isEmpty(customerInfoParam.getQuery().getPhone())) {
                Predicate predicate = criteriaBuilder.like(root.get("phone").as(String.class), "%" + customerInfoParam.getQuery().getPhone() + "%");
                predicates.add(predicate);
            }
            
            //类别  select[客户,供应商,内部] default[客户]
            if (!StringUtils.isEmpty(customerInfoParam.getQuery().getType())) {
                Predicate predicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + customerInfoParam.getQuery().getType() + "%");
                predicates.add(predicate);
            }
            
            if (predicates.size() == 0) {
                return null;
            }
            Predicate[] predicateArr = new Predicate[predicates.size()];
            predicateArr = predicates.toArray(predicateArr);
            return criteriaBuilder.and(predicateArr);
        };
        Page<CustomerInfo> customerInfoPage = customerInfoDao.findAll(specification, pageable);
        return pageToDto(customerInfoPage, pageable);
    }

}
