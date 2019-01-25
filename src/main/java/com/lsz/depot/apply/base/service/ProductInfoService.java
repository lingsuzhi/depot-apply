package com.lsz.depot.apply.base.service;

import com.lsz.depot.apply.po.ProductInfo;
import com.lsz.depot.core.common.PageParam;
import com.lsz.depot.apply.base.param.ProductInfoParam;
import com.lsz.depot.core.dao.DaoUtil;
import com.lsz.depot.core.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import com.lsz.depot.core.utils.UuidMd5;
import com.lsz.depot.apply.base.dao.ProductInfoDao;
import org.springframework.data.domain.Pageable;
import com.lsz.depot.apply.base.dto.ProductInfoDto;
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
public class ProductInfoService {

    @Autowired
    private ProductInfoDao productInfoDao;
    
    public ProductInfoDto findByUuid(String uuid) {
        ProductInfo productInfo = productInfoDao.findByUuid(uuid);
        return BeanUtil.copyBean(productInfo, ProductInfoDto.class);
    }
    
    public String addNew(ProductInfoDto productInfoDto) {
        ProductInfo productInfo = BeanUtil.copyBean(productInfoDto, ProductInfo.class);
        String uuid = UuidMd5.uuidWith22Bit();
        log.info("addNew productInfo：{}", productInfoDto);
        
        productInfo.setUuid(uuid);
        productInfoDao.save(productInfo);
        return uuid;
    }
    
    public Boolean delByUuid(String uuid) {
        productInfoDao.deleteByUuid(uuid);
        return true;
    }
    
    public String update(ProductInfoDto productInfoDto) {
        String uuid = productInfoDto.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        ProductInfo productInfo = productInfoDao.findByUuid(uuid);
        if (productInfo == null) {
            log.info("update数据不存在，请查证uuid {}", uuid);
            return null;
        }
        ProductInfo info = BeanUtil.copyBeanNotNull(productInfoDto, productInfo);
        productInfoDao.save(info);
        return uuid;
    }
    
    private Page pageToDto(Page<ProductInfo> productInfoPage, Pageable pageable) {
        if (productInfoPage == null || CollectionUtils.isEmpty(productInfoPage.getContent())) {
            return productInfoPage;
        }
        List<ProductInfo> content = productInfoPage.getContent();
        List<ProductInfoDto> list = new ArrayList<>();
        for (ProductInfo productInfo : content) {
            ProductInfoDto productInfoDto = BeanUtil.copyBean(productInfo, ProductInfoDto.class);
            list.add(productInfoDto);
        }
        return new PageImpl<>(list, pageable, productInfoPage.getTotalElements());
    }
    
    public Page<ProductInfoDto> findAll(PageParam<ProductInfoParam> productInfoParam) {
        Pageable pageable = DaoUtil.getPageable(productInfoParam);
        Specification<ProductInfo> specification = (Specification<ProductInfo>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //编号 <key><required>
            if (!StringUtils.isEmpty(productInfoParam.getQuery().getNumber())) {
                Predicate predicate = criteriaBuilder.like(root.get("number").as(String.class), "%" + productInfoParam.getQuery().getNumber() + "%");
                predicates.add(predicate);
            }
            
            //名称 <required>
            if (!StringUtils.isEmpty(productInfoParam.getQuery().getName())) {
                Predicate predicate = criteriaBuilder.like(root.get("name").as(String.class), "%" + productInfoParam.getQuery().getName() + "%");
                predicates.add(predicate);
            }
            
            //规格 
            if (!StringUtils.isEmpty(productInfoParam.getQuery().getSpec())) {
                Predicate predicate = criteriaBuilder.like(root.get("spec").as(String.class), "%" + productInfoParam.getQuery().getSpec() + "%");
                predicates.add(predicate);
            }
            
            //类别 
            if (!StringUtils.isEmpty(productInfoParam.getQuery().getType())) {
                Predicate predicate = criteriaBuilder.like(root.get("type").as(String.class), "%" + productInfoParam.getQuery().getType() + "%");
                predicates.add(predicate);
            }
            
            if (predicates.size() == 0) {
                return null;
            }
            Predicate[] predicateArr = new Predicate[predicates.size()];
            predicateArr = predicates.toArray(predicateArr);
            return criteriaBuilder.and(predicateArr);
        };
        Page<ProductInfo> productInfoPage = productInfoDao.findAll(specification, pageable);
        return pageToDto(productInfoPage, pageable);
    }

}
