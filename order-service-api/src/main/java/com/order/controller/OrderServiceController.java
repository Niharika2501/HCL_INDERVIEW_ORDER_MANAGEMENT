package com.order.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.model.OrderServiceResponse;
import com.order.repo.entities.OrderDetail;
import com.order.service.OrderDetailService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/sg/v1")
@Slf4j
public class OrderServiceController {
	
	@Autowired
	private OrderDetailService service;

	@GetMapping("/order/{orderId}")
	public OrderDetail getOrderById(@PathVariable("orderId") @NotBlank Long orderId) {
		log.info("Order # {} received.", orderId);
		return service.getOrderDetail(orderId);
	}

	@PostMapping("/order")
	public OrderServiceResponse createOrder(@Valid @RequestBody OrderDetail orderDetail) {
		log.info("Order details {} received.", orderDetail.toString());
		return service.saveOrderDetail(orderDetail);
	}
}