package com.order.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.google.gson.Gson;
import com.order.exception.OrderNotFoundException;
import com.order.model.OrderServiceResponse;
import com.order.repo.entities.OrderDetail;
import com.order.repo.entities.OrderItem;
import com.order.service.OrderDetailService;

public class OrderServiceControllerTest {
	@InjectMocks
	private OrderServiceController orderServiceController;
	@Autowired
	private MockMvc mvc;
	@Mock
	private OrderDetailService service;

	@BeforeEach
	public void setup() {
		orderServiceController = new OrderServiceController();
		MockitoAnnotations.initMocks(this);
		Mockito.spy(OrderServiceController.class);
		this.mvc = MockMvcBuilders.standaloneSetup(orderServiceController).build();
	}

	@Test
	public void getOrderById_thenStatus200() throws Exception {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(1l);
		when(service.getOrderDetail(Mockito.anyLong())).thenReturn(orderDetail);
		mvc.perform(get("/api/sg/v1/order/{orderId}", 3).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getOrderById_thenStatus404() throws Exception {
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setOrderId(1l);
		when(service.getOrderDetail(Mockito.anyLong())).thenThrow(OrderNotFoundException.class);

		Assertions.assertThrows(NestedServletException.class, () -> {
			mvc.perform(get("/api/sg/v1/order/{orderId}", 3).contentType(MediaType.APPLICATION_JSON));
		});
	}

	@Test
	public void createOrder_thenStatus200() throws Exception {
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
		Gson json = new Gson();
		when(service.saveOrderDetail(Mockito.any())).thenReturn(new OrderServiceResponse("Save"));
		mvc.perform(post("/api/sg/v1/order").contentType(MediaType.APPLICATION_JSON).content(json.toJson(orderDetail)))
				.andExpect(status().isOk());
	}

	@Test
	public void createOrder_thenStatus400() throws Exception {
		OrderItem orderItem = new OrderItem();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(orderItem);
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setShippingAddress("XYZ");
		orderDetail.setTotalAmount(11.6d);
		orderDetail.setOrderItems(list);
		Gson json = new Gson();
		mvc.perform(post("/api/sg/v1/order").contentType(MediaType.APPLICATION_JSON).content(json.toJson(orderDetail)))
				.andExpect(status().isBadRequest());
	}
}
