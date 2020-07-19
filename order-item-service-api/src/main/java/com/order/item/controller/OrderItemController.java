package com.order.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.item.model.Order;
import com.order.item.services.DataProcessingService;

import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/sg/v1")
@Slf4j
public class OrderItemController {
	
	@Autowired
	private DataProcessingService dataProcessingService;
	
    @GetMapping("/orderItem/{orderId}")
    public List<Order> getOrderItem(@PathVariable("orderId") Long orderId){
    	log.info("getOrderItem method is Calling");
    	return dataProcessingService.processGetRequest(orderId);
    }

    @PostMapping("/orderItem")
    public ResponseEntity<Object> createOrderItem(@RequestBody @Valid  List<Order> orders){
        dataProcessingService.processPostRequest(orders);
        return new ResponseEntity<Object>("Data Inserted Successfully", HttpStatus.OK);

    }
}