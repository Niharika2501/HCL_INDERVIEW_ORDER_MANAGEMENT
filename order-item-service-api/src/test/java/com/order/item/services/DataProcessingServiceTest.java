package com.order.item.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.order.item.model.Order;
import com.order.item.repo.OrderItemRepository;
import com.order.item.repo.entities.OrderItem;
import com.order.item.repo.entities.OrderItemId;

public class DataProcessingServiceTest {
	@InjectMocks
	private DataProcessingService dataProcessingService;
	@Mock
	private OrderItemRepository orderItemRepository;

	@BeforeEach
	public void setup() {
		dataProcessingService = new DataProcessingService();
		MockitoAnnotations.initMocks(this);
		Mockito.spy(DataProcessingService.class);
	}

	@Test
	public void processPostRequest_Sucsess() {
		Order orderItem = new Order();
		orderItem.setOrderId(1l);
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		List<OrderItem> OrderItems = new ArrayList<OrderItem>();
		when(orderItemRepository.saveAll(Mockito.any())).thenReturn(OrderItems);
		assertEquals(true, dataProcessingService.processPostRequest(list));
	}

	@Test
	public void processPostRequest_Exception() {
		Order orderItem = new Order();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		Assertions.assertThrows(RuntimeException.class, () -> {
			dataProcessingService.processPostRequest(list);
		});
	}

	@Test
	public void processGetRequest_Sucess() {
		OrderItemId orderItemId = new OrderItemId();
		orderItemId.setOrderId(1l);
		orderItemId.setProductCode("abc");
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(1l);
		orderItem.setProductName("aaa");
		orderItem.setOrderItemId(orderItemId);
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(orderItem);
		when(orderItemRepository.findByOrderItemIdOrderId(Mockito.anyLong())).thenReturn(list);
		assertEquals(1, dataProcessingService.processGetRequest(1l).size());
	}

}
