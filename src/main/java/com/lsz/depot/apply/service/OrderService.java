package com.lsz.depot.apply.service;

import com.alibaba.fastjson.JSONObject;
import com.lsz.depot.apply.dao.OrderDao;
import com.lsz.depot.apply.po.OrderInfo;
import com.lsz.depot.apply.vo.ProductInfoVO;
import com.lsz.depot.core.dto.OrderInfoDTO;
import com.lsz.depot.core.utils.BeanUtil;
import com.lsz.depot.core.utils.LocalDateUtil;
import com.lsz.depot.core.utils.UuidMd5;
import com.lsz.depot.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderDao orderInfoDao;

    public static String toOrderNumber(String type, LocalDateTime date, Integer number) {
        String numStr = String.valueOf(number);
        if (numStr.length() < 3) {
            numStr = "000".substring(0, 3 - numStr.length()) + numStr;
        }
        return type.toUpperCase() + "-" + LocalDateUtil.toStr(date) + "-" + numStr;
    }

    public String newOrderNumber(String type, LocalDateTime date) {
        Integer number = findNewDaySerial(type, date);
        return toOrderNumber(type, date, number);
    }

    public OrderInfoDTO findByDayMax(String type, LocalDateTime date) {
        OrderInfo orderInfo = orderInfoDao.findFirstByTypeAndDateOrderByDaySerialDesc(type, date);
        if (orderInfo == null) {
            orderInfo = orderInfoDao.findFirstByTypeOrderByDaySerialDesc(type);
        }
        return BeanUtil.copyBean(orderInfo, OrderInfoDTO.class);
    }

    private Integer findNewDaySerial(String type, LocalDateTime date) {
        OrderInfo orderInfo = orderInfoDao.findFirstByTypeAndDateOrderByDaySerialDesc(type, date);
        Integer number = 1;
        if (orderInfo != null) {
            number = orderInfo.getDaySerial() + 1;
        }
        return number;
    }

    public String save(OrderInfoDTO orderInfoDTO) {
        if (StringUtils.isEmpty(orderInfoDTO.getNumber())) {
            throw new BusinessException("5545", "编号不能为空，请查证");
        }
        if (StringUtils.isEmpty(orderInfoDTO.getType())) {
            throw new BusinessException("5547", "类型不能为空，请查证");
        }
        String data = orderInfoDTO.getData();
        if (!StringUtils.isEmpty(data)) {
            List<ProductInfoVO> list = JSONObject.parseArray(data, ProductInfoVO.class);
            BigDecimal countTotal = BigDecimal.ZERO;
            BigDecimal countAmount = BigDecimal.ZERO;

            if (!CollectionUtils.isEmpty(list)) {
                for (ProductInfoVO productInfoVO : list) {
                    //  BigDecimal proce = productInfoVO.getProce() != null ? productInfoVO.getProce() : BigDecimal.ZERO;
                    BigDecimal count = productInfoVO.getCount() != null ? productInfoVO.getCount() : BigDecimal.ZERO;
                    BigDecimal total = productInfoVO.getTotal() != null ? productInfoVO.getTotal() : BigDecimal.ZERO;

                    countTotal = countTotal.add(total);
                    countAmount = countAmount.add(count);

                }
            }
            orderInfoDTO.setCountTotal(countTotal);
            orderInfoDTO.setCountAmount(countAmount);
            //暂时不覆盖
        }
        if (StringUtils.isEmpty(orderInfoDTO.getUuid())) {
            //新增
            OrderInfo byNumber = orderInfoDao.findByNumber(orderInfoDTO.getNumber());
            Integer daySerial = this.findNewDaySerial(orderInfoDTO.getType(), orderInfoDTO.getDate());
            if (byNumber != null) {
                String number = newOrderNumber(orderInfoDTO.getType(), orderInfoDTO.getDate());
                orderInfoDTO.setNumber(number);
            }

            orderInfoDTO.setDaySerial(daySerial);
            return addNew(orderInfoDTO);
        } else {
            return update(orderInfoDTO);
        }
    }


    public String addNew(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = BeanUtil.copyBean(orderInfoDTO, OrderInfo.class);
        String uuid = UuidMd5.uuidWith22Bit();
        log.info("addNew orderInfo：{}", orderInfoDTO);

        orderInfo.setUuid(uuid);
        orderInfoDao.save(orderInfo);
        return uuid;
    }

    public Boolean delByUuid(String uuid) {
        orderInfoDao.deleteByUuid(uuid);
        return true;
    }

    public String update(OrderInfoDTO orderInfoDTO) {
        String uuid = orderInfoDTO.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        OrderInfo orderInfo = orderInfoDao.findByUuid(uuid);
        if (orderInfo == null) {
            log.info("update数据不存在，请查证uuid {}", uuid);
            return null;
        }
        OrderInfo info = BeanUtil.copyBeanNotNull(orderInfoDTO, orderInfo);
        orderInfoDao.save(info);
        return uuid;
    }

}
