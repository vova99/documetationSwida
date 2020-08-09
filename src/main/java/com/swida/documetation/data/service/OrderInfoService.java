package com.swida.documetation.data.service;

import com.swida.documetation.data.entity.OrderInfo;
import com.swida.documetation.data.entity.subObjects.ContrAgent;
import com.swida.documetation.data.enums.StatusOfOrderInfo;

import java.util.List;

public interface OrderInfoService {
    void save(OrderInfo orderInfo);
    OrderInfo findById(int id);
    List<OrderInfo> getOrdersByStatusOfOrder(StatusOfOrderInfo status);
    List<OrderInfo> getOrdersListByAgent(int agentId);
    List<OrderInfo> findAll();
    void deleteByID(int id);
}
