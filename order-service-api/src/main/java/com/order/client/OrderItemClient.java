package com.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.order.repo.entities.OrderItem;

@FeignClient("order-item-service-api")
public interface OrderItemClient {

	@GetMapping("orderitem/api/sg/v1/orderItem/{orderId}")
	List<OrderItem> getOrderItemList(@PathVariable("orderId") Long orderId);

	@PostMapping("orderitem/api/sg/v1/orderItem")
	String saveOrder(@RequestBody List<OrderItem> orderItems);
}