package com.lsz.depot.apply.service;

import com.alibaba.fastjson.JSONObject;
import com.lsz.depot.apply.dao.OrderChangeDao;
import com.lsz.depot.apply.dao.OrderDao;
import com.lsz.depot.apply.dao.SuggestionDao;
import com.lsz.depot.apply.enums.OrderChangeOperate;
import com.lsz.depot.apply.po.OrderChangeInfo;
import com.lsz.depot.apply.po.OrderInfo;
import com.lsz.depot.apply.po.SuggestionInfo;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    private static List<String> SubStockTypes = Arrays.asList("XS", "LC");
    private static List<String> AddStockTypes = Arrays.asList("TR", "JR");

    @Autowired
    private OrderDao orderInfoDao;
    @Autowired
    private OrderChangeDao orderChangeDao;
    @Autowired
    private SuggestionDao dao;

    private static String toOrderNumber(String type, LocalDateTime date, Integer number) {

        String numStr = String.valueOf(number);
        if (numStr.length() < 3) {
            numStr = "000".substring(0, 3 - numStr.length()) + numStr;
        }

        return type.toUpperCase() + "-" + LocalDateUtil.toStr(date) + "-" + numStr;
    }

    private static OrderChangeOperate toOperate(String type) {
        if (AddStockTypes.contains(type)) {
            return OrderChangeOperate.ADD;
        }
        if (SubStockTypes.contains(type)) {
            return OrderChangeOperate.SUB;
        }
        return OrderChangeOperate.TRANSFER;
    }

    private static OrderInfoDTO toDto(OrderInfo orderInfo) {
        return BeanUtil.copyBean(orderInfo, OrderInfoDTO.class);
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
        return toDto(orderInfo);
    }

    private Integer findNewDaySerial(String type, LocalDateTime date) {
        OrderInfo orderInfo = orderInfoDao.findFirstByTypeAndDateOrderByDaySerialDesc(type, date);
        Integer number = 1;
        if (orderInfo != null) {
            number = orderInfo.getDaySerial() + 1;
        }
        return number;
    }

    @Transactional
    public String save(OrderInfoDTO orderInfoDTO) throws Exception {
        if (StringUtils.isEmpty(orderInfoDTO.getUuid())) {
            throw new BusinessException("5545", "编号不能为空，请查证");
        }
        if (StringUtils.isEmpty(orderInfoDTO.getType())) {
            throw new BusinessException("5547", "类型不能为空，请查证");
        }
        String data = orderInfoDTO.getData();
        if (!StringUtils.isEmpty(data)) {
            List<ProductInfoVO> list = JSONObject.parseArray(data, ProductInfoVO.class);
            BigDecimal countTotal = BigDecimal.ZERO;
            Double countAmount = 0.0;

            if (!CollectionUtils.isEmpty(list)) {
                for (ProductInfoVO productInfoVO : list) {
                    //  BigDecimal proce = productInfoVO.getProce() != null ? productInfoVO.getProce() : BigDecimal.ZERO;
                    Double count = productInfoVO.getCount() != null ? productInfoVO.getCount() : 0;
                    BigDecimal total = productInfoVO.getTotal() != null ? productInfoVO.getTotal() : BigDecimal.ZERO;

                    countTotal = countTotal.add(total);
                    countAmount += count;

                }
            }
            orderInfoDTO.setCountTotal(countTotal);
            orderInfoDTO.setCountAmount(countAmount);
            //暂时不覆盖
        }


        OrderInfo orderInfo = orderInfoDao.findByUuid(orderInfoDTO.getUuid());
        if (orderInfo == null) {
            //新增
            Integer daySerial = this.findNewDaySerial(orderInfoDTO.getType(), orderInfoDTO.getDate());
            orderInfoDTO.setDaySerial(daySerial);
            orderInfoDTO.setUuid(toOrderNumber(orderInfoDTO.getType(), orderInfoDTO.getDate(), daySerial));
            return addNew(orderInfoDTO);
        } else {
            return update(orderInfoDTO, orderInfo);
        }
    }

    @Transactional
    public String addNew(OrderInfoDTO orderInfoDTO) {
        OrderInfo orderInfo = BeanUtil.copyBean(orderInfoDTO, OrderInfo.class);
        orderInfoDao.save(orderInfo);
        //操作库存
        orderChangeDao.save(getOrderChangeInfo(orderInfo, true));

        return orderInfo.getUuid();
    }
    public OrderInfoDTO leftOrder(String uuid) {
        OrderInfo orderInfo = orderInfoDao.findFirstByUuidLessThanOrderByUuidDesc(uuid);
        if (orderInfo == null) {
            log.info("leftOrder数据不存在，请查证uuid {}", uuid);
            return null;
        }
        return toDto(orderInfo);
    }
    public OrderInfoDTO rightOrder(String uuid) {
        OrderInfo orderInfo = orderInfoDao.findFirstByUuidGreaterThanOrderByUuid(uuid);
        if (orderInfo == null) {
            log.info("rightOrder数据不存在，请查证uuid {}", uuid);
            return null;
        }
        return toDto(orderInfo);
    }

    @Transactional
    public Boolean delByUuid(String uuid) {
        OrderInfo orderInfo = orderInfoDao.findByUuid(uuid);
        if (orderInfo == null) {
            log.info("delByUuid数据不存在，请查证uuid {}", uuid);
            return null;
        }
        OrderChangeInfo orderChangeInfoOld = getOrderChangeInfo(orderInfo, false);
        orderInfoDao.deleteByUuid(uuid);
        orderChangeDao.save(orderChangeInfoOld);
        return true;
    }

    private OrderChangeInfo getOrderChangeInfo(OrderInfo orderInfo, Boolean isAdd) {
        OrderChangeOperate operate = toOperate(orderInfo.getType());
        OrderChangeInfo orderChangeInfo = new OrderChangeInfo();
        orderChangeInfo.setData(orderInfo.getData());
        orderChangeInfo.setOperate(operate.toString());
        orderChangeInfo.setCountTotal(orderInfo.getCountTotal());
        orderChangeInfo.setUuid(orderInfo.getUuid());
        orderChangeInfo.setStatus(false);
        orderChangeInfo.setIsAdd(isAdd);
        return orderChangeInfo;
    }

    @Transactional
    public String update(OrderInfoDTO orderInfoDTO, OrderInfo orderInfo) {
        OrderChangeInfo orderChangeInfoOld = getOrderChangeInfo(orderInfo, false);
        //这3个不在这里修改
        orderInfoDTO.setType(null);
        orderInfoDTO.setDate(null);
        orderInfoDTO.setDaySerial(null);
        //保存单据
        OrderInfo info = BeanUtil.copyBeanNotNull(orderInfoDTO, orderInfo);
        orderInfoDao.save(info);
        OrderChangeInfo orderChangeInfo = getOrderChangeInfo(orderInfo, true);

        //库存
        if (!orderChangeEquals(orderChangeInfoOld, orderChangeInfo)) {
            orderChangeDao.save(orderChangeInfoOld);
            orderChangeDao.save(orderChangeInfo);
        }
        return orderInfo.getUuid();
    }

    private Boolean orderChangeEquals(OrderChangeInfo orderChangeInfoOld, OrderChangeInfo orderChangeInfo) {
        if (orderChangeInfoOld.getCountTotal().compareTo(orderChangeInfo.getCountTotal()) == 0) {
            return UuidMd5.hasStrByMd5(orderChangeInfo.getData(), orderChangeInfoOld.getData());
        }
        return false;
    }
}
