package com.order.item.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.item.model.Order;
import com.order.item.repo.OrderItemRepository;
import com.order.item.repo.entities.OrderItem;
import com.order.item.repo.entities.OrderItemId;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataProcessingService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	public List<Order> processGetRequest(Long orderId) {

		log.info("<<processGetRequest method started for order # >> " + orderId);

		List<OrderItem> orderItems = orderItemRepository.findByOrderItemIdOrderId(orderId);

		List<Order> orders = new ArrayList<Order>();

		orderItems.forEach(orderItem -> {
			Order order = new Order();
			order.setOrderId(orderItem.getOrderItemId().getOrderId());
			order.setProductCode(orderItem.getOrderItemId().getProductCode());
			order.setProductName(orderItem.getProductName());
			order.setQuantity(orderItem.getQuantity());
			orders.add(order);
		});

		log.info("<<processGetRequest method end for order # >> " + orderId);

		return orders;
	}

	public boolean processPostRequest(List<Order> orders) {

		log.info("<<processPostRequest method started >>");

		List<OrderItem> OrderItems = new ArrayList<OrderItem>();

		orders.forEach(order -> {
			OrderItem orderItem = new OrderItem();
			OrderItemId orderItemId = new OrderItemId();
			orderItemId.setOrderId(order.getOrderId());
			orderItemId.setProductCode(order.getProductCode());
			orderItem.setProductName(order.getProductName());
			orderItem.setQuantity(order.getQuantity());
			orderItem.setOrderItemId(orderItemId);
			OrderItems.add(orderItem);
		});

		orderItemRepository.saveAll(OrderItems);

		log.info("<<processPostRequest method ended >>");

		return true;
	}

}