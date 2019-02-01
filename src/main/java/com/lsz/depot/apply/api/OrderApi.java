package com.lsz.depot.apply.api;

import com.lsz.depot.apply.service.OrderService;
import com.lsz.depot.core.common.ResponseInfo;
import com.lsz.depot.core.dto.OrderInfoDTO;
import com.lsz.depot.core.utils.LocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 初始化
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderApi {

    @Autowired
    private OrderService service;


    /**
     * 获取最新单号
     *
     * @return
     */
    @RequestMapping(value = "/newOrderNumber")
    public ResponseInfo<String> newOrderNumber(@RequestParam String type, @RequestParam String dateStr) {
        LocalDateTime date = LocalDateUtil.parse(dateStr);
        return ResponseInfo.assertion(service.newOrderNumber(type, date));
    }

    /**
     * 获取某天最后一单
     * 如果没有返回最后一份单
     *
     * @return
     */
    @RequestMapping(value = "/findByDayMax")
    public ResponseInfo<OrderInfoDTO> findByDayMax(@RequestParam String type, @RequestParam String dateStr) {
        LocalDateTime date = LocalDateUtil.parse(dateStr);
        return ResponseInfo.success(service.findByDayMax(type, date));
    }

    @RequestMapping(value = "/save")
    public ResponseInfo<String> save(@RequestBody OrderInfoDTO orderInfoDTO) throws Exception {
        return ResponseInfo.assertion(service.save(orderInfoDTO));
    }

    @RequestMapping(value = "/delByUuid")
    public ResponseInfo<Boolean> delByUuid(@RequestParam String uuid) {
        return ResponseInfo.assertion(service.delByUuid(uuid));
    }

    @RequestMapping(value = "/leftOrder")
    public ResponseInfo<OrderInfoDTO> leftOrder(@RequestParam String uuid) {
        return ResponseInfo.success(service.leftOrder(uuid));
    }

    @RequestMapping(value = "/rightOrder")
    public ResponseInfo<OrderInfoDTO> rightOrder(@RequestParam String uuid) {
        return ResponseInfo.success(service.rightOrder(uuid));
    }

}
