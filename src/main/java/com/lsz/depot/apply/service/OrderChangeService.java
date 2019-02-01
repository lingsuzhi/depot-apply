package com.lsz.depot.apply.service;

import com.alibaba.fastjson.JSONObject;
import com.lsz.depot.apply.dao.OrderChangeDao;
import com.lsz.depot.apply.dao.ProductDao;
import com.lsz.depot.apply.dao.SuggestionDao;
import com.lsz.depot.apply.enums.OrderChangeOperate;
import com.lsz.depot.apply.po.OrderChangeInfo;
import com.lsz.depot.apply.po.SuggestionInfo;
import com.lsz.depot.apply.vo.ProductInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class OrderChangeService {

    @Autowired
    private OrderChangeDao dao;
    @Autowired
    private ProductDao productInfoDao;
    @Autowired
    private SuggestionDao suggestionDao;

    @Scheduled(cron = "0/5 * * * * ?")  //每隔2秒执行一次定时任务
    @Transactional //这里要记得加事务
    public void taskOrderChange() {
        List<OrderChangeInfo> list = dao.findAllByStatusFalse();
        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        log.info("taskOrderChange 查询到数据 {}", list.size());
        for (OrderChangeInfo orderChangeInfo : list) {
            changeDo(orderChangeInfo);
        }
    }

    @Transactional
    public void changeDo(OrderChangeInfo orderChangeInfo) {
        String data = orderChangeInfo.getData();
        if (!StringUtils.isEmpty(data)) {
            List<ProductInfoVO> list = JSONObject.parseArray(data, ProductInfoVO.class);
            if (!CollectionUtils.isEmpty(list)) {
                for (ProductInfoVO productInfoVO : list) {
                    if (!StringUtils.isEmpty(productInfoVO.getNumber())) {
                        if (OrderChangeOperate.ADD.toString().equals(orderChangeInfo.getOperate())) {
                            if (BooleanUtils.isTrue(orderChangeInfo.getIsAdd())){
                                productInfoDao.updateAddCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }else{
                                productInfoDao.subAddCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }
                        } else if (OrderChangeOperate.SUB.toString().equals(orderChangeInfo.getOperate())) {
                            if (BooleanUtils.isTrue(orderChangeInfo.getIsAdd())){
                                productInfoDao.updateSubCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }else{
                                productInfoDao.subSubCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }
                        } else if (OrderChangeOperate.TRANSFER.toString().equals(orderChangeInfo.getOperate())) {
                            if (BooleanUtils.isTrue(orderChangeInfo.getIsAdd())){
                                productInfoDao.updateTransferCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }else{
                                productInfoDao.subTransferCount(productInfoVO.getNumber(), productInfoVO.getCount());
                            }

                        }
                    }
                }
            }
            orderChangeInfo.setStatus(true);
            dao.save(orderChangeInfo);
        }
    }
}
