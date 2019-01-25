package com.lsz.depot.apply.base.service;

import com.lsz.depot.apply.po.ProductTypeInfo;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.param.ProductTypeInfoParam;
import com.lsz.depot.core.dao.DaoUtil;
import com.lsz.depot.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import com.lsz.depot.core.utils.UuidMd5;
import com.lsz.depot.apply.base.dao.ProductTypeInfoDao;
import org.springframework.data.domain.Pageable;
import com.lsz.depot.apply.base.dto.ProductTypeInfoDto;
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
public class ProductTypeInfoService {

    @Autowired
    private ProductTypeInfoDao productTypeInfoDao;
    
    public ProductTypeInfoDto findByUuid(String uuid) {
        ProductTypeInfo productTypeInfo = productTypeInfoDao.findByUuid(uuid);
        return BeanUtil.copyBean(productTypeInfo, ProductTypeInfoDto.class);
    }
    
    public String addNew(ProductTypeInfoDto productTypeInfoDto) {
        ProductTypeInfo productTypeInfo = BeanUtil.copyBean(productTypeInfoDto, ProductTypeInfo.class);
        String uuid = UuidMd5.uuidWith22Bit();
        log.info("addNew productTypeInfo：{}", productTypeInfoDto);
        
        productTypeInfo.setUuid(uuid);
        productTypeInfoDao.save(productTypeInfo);
        return uuid;
    }
    
    public Boolean delByUuid(String uuid) {
        productTypeInfoDao.deleteByUuid(uuid);
        return true;
    }
    
    public String update(ProductTypeInfoDto productTypeInfoDto) {
        String uuid = productTypeInfoDto.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        ProductTypeInfo productTypeInfo = productTypeInfoDao.findByUuid(uuid);
        if (productTypeInfo == null) {
            log.info("update数据不存在，请查证uuid {}", uuid);
            return null;
        }
        ProductTypeInfo info = BeanUtil.copyBeanNotNull(productTypeInfoDto, productTypeInfo);
        productTypeInfoDao.save(info);
        return uuid;
    }
    
    private Page pageToDto(Page<ProductTypeInfo> productTypeInfoPage, Pageable pageable) {
        if (productTypeInfoPage == null || CollectionUtils.isEmpty(productTypeInfoPage.getContent())) {
            return productTypeInfoPage;
        }
        List<ProductTypeInfo> content = productTypeInfoPage.getContent();
        List<ProductTypeInfoDto> list = new ArrayList<>();
        for (ProductTypeInfo productTypeInfo : content) {
            ProductTypeInfoDto productTypeInfoDto = BeanUtil.copyBean(productTypeInfo, ProductTypeInfoDto.class);
            list.add(productTypeInfoDto);
        }
        return new PageImpl<>(list, pageable, productTypeInfoPage.getTotalElements());
    }
    
    public Page<ProductTypeInfoDto> findAll(PageParam<ProductTypeInfoParam> productTypeInfoParam) {
        Pageable pageable = DaoUtil.getPageable(productTypeInfoParam);
        Specification<ProductTypeInfo> specification = (Specification<ProductTypeInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //名称 <required>
            if (!StringUtils.isEmpty(productTypeInfoParam.getQuery().getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + productTypeInfoParam.getQuery().getName() + "%");
                predicates.add(predicate);
            }
            
            //类型  select[类别,包含文字] default[类别]
            if (!StringUtils.isEmpty(productTypeInfoParam.getQuery().getType())) {
                Predicate predicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + productTypeInfoParam.getQuery().getType() + "%");
                predicates.add(predicate);
            }
            
            if (predicates.size() == 0) {
                return null;
            }
            Predicate[] predicateArr = new Predicate[predicates.size()];
            predicateArr = predicates.toArray(predicateArr);
            return criteriaBuilder.and(predicateArr);
        };
        Page<ProductTypeInfo> productTypeInfoPage = productTypeInfoDao.findAll(specification, pageable);
        return pageToDto(productTypeInfoPage, pageable);
    }

}
