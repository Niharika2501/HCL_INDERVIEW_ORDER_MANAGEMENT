package com.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.order.client.OrderItemClient;
import com.order.exception.OrderNotFoundException;
import com.order.repo.OrderDetailRepository;
import com.order.repo.entities.OrderDetail;
import com.order.repo.entities.OrderItem;

public class OrderDetailServiceTest {
	@InjectMocks
	private OrderDetailService orderDetailService;
	@Mock
	private OrderDetailRepository orderDetailRepository;

	@Mock
	private OrderItemClient orderItemClient;

	@BeforeEach
	public void setup() {
		orderDetailService = new OrderDetailService();
		MockitoAnnotations.initMocks(this);
		Mockito.spy(OrderDetailService.class);
	}

	@Test
	public void saveOrderDetail_Sucsess() {
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(orderItem);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCustomerName("ABC");
		orderDetail.setShippingAddress("XYZ");
		orderDetail.setTotalAmount(11.6d);
		orderDetail.setOrderItems(list);
		orderDetail.setOrderId(1l);
		when(orderDetailRepository.save(Mockito.any())).thenReturn(orderDetail);
		when(orderItemClient.saveOrder(Mockito.any())).thenReturn("aa");
		assertEquals("Order created successfully.Your Order Id:1",
				orderDetailService.saveOrderDetail(orderDetail).getMessage());
	}

	@Test
	public void saveOrderDetail_Exception() {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCustomerName("ABC");
		orderDetail.setShippingAddress("XYZ");
		orderDetail.setTotalAmount(11.6d);
		when(orderDetailRepository.save(Mockito.any())).thenThrow(new IllegalArgumentException("Exception"));
		Assertions.assertThrows(RuntimeException.class, () -> {
			orderDetailService.saveOrderDetail(orderDetail);
		});
	}

	@Test
	public void getOrderDetail_Sucess() {
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(orderItem);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setCustomerName("ABC");
		orderDetail.setShippingAddress("XYZ");
		orderDetail.setTotalAmount(11.6d);
		orderDetail.setOrderItems(list);
		orderDetail.setOrderId(1l);
		when(orderDetailRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(orderDetail));
		when(orderItemClient.getOrderItemList(Mockito.anyLong())).thenReturn(list);
		assertEquals("ABC", orderDetailService.getOrderDetail(1l).getCustomerName());
	}

	@Test
	public void getOrderDetail_Exception() {
		Assertions.assertThrows(OrderNotFoundException.class, () -> {
			orderDetailService.sendErrorResponse(1l);
		});
	}
}
