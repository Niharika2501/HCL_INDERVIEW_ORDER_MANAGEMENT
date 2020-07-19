package com.order.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.order.client.OrderItemClient;
import com.order.exception.OrderNotFoundException;
import com.order.model.OrderServiceResponse;
import com.order.repo.OrderDetailRepository;
import com.order.repo.entities.OrderDetail;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private OrderItemClient orderItemClient;

	@HystrixCommand(fallbackMethod = "sendErrorResponse")
	public OrderDetail getOrderDetail(Long orderId) {
		OrderDetail orderDetail = orderDetailRepository.findById(orderId).get();
		orderDetail.setOrderItems(orderItemClient.getOrderItemList(orderId));
		return orderDetail;
	}

	@Transactional
	public OrderServiceResponse saveOrderDetail(OrderDetail orderDetail) {
		try {
			log.info("Order Id : {}", orderDetail.toString());
			long orderId = (orderDetailRepository.save(orderDetail)).getOrderId();
			log.info("Order Id : {}", orderId);
			orderDetail.getOrderItems().forEach(oi -> oi.setOrderId(orderId));
			orderItemClient.saveOrder(orderDetail.getOrderItems());
			log.info("Order Details:{}", orderDetail.toString());
			return new OrderServiceResponse("Order created successfully.Your Order Id:" + orderId);
		} catch (Exception ex) {
			log.error("exception occurred : {}", ex.getMessage());
			throw new RuntimeException("order creation exception");
		}
	}

	public OrderDetail sendErrorResponse(Long orderId) {
		throw new OrderNotFoundException("Order Not Found");
	}
}