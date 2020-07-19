package com.order.item.controller;

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
import com.order.item.model.Order;
import com.order.item.services.DataProcessingService;

public class OrderItemControllerTest {
	@InjectMocks
	private OrderItemController orderItemController;
	@Autowired
	private MockMvc mvc;
	@Mock
	private DataProcessingService service;

	@BeforeEach
	public void setup() {
		orderItemController = new OrderItemController();
		MockitoAnnotations.initMocks(this);
		Mockito.spy(OrderItemController.class);
		this.mvc = MockMvcBuilders.standaloneSetup(orderItemController).build();
	}

	@Test
	public void getOrderById_thenStatus200() throws Exception {
		Order orderItem = new Order();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		when(service.processGetRequest(Mockito.anyLong())).thenReturn(list);
		mvc.perform(get("/api/sg/v1/orderItem/{orderId}", 3).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getOrderById_thenStatus404() throws Exception {
		Order orderItem = new Order();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		when(service.processGetRequest(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
		Assertions.assertThrows(NestedServletException.class, () -> {
			mvc.perform(get("/api/sg/v1/orderItem/{orderId}", 3).contentType(MediaType.APPLICATION_JSON));
		});
	}

	@Test
	public void createOrder_thenStatus200() throws Exception {
		Order orderItem = new Order();
		orderItem.setQuantity(1l);
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		Gson json = new Gson();
		when(service.processPostRequest(Mockito.any())).thenReturn(true);
		mvc.perform(post("/api/sg/v1/orderItem").contentType(MediaType.APPLICATION_JSON).content(json.toJson(list)))
				.andExpect(status().isOk());
	}

	@Test
	public void createOrder_thenStatus400() throws Exception {
		Order orderItem = new Order();
		orderItem.setProductCode("aa");
		orderItem.setProductName("aaa");
		List<Order> list = new ArrayList<Order>();
		list.add(orderItem);
		Gson json = new Gson();
		when(service.processPostRequest(Mockito.any())).thenThrow(IllegalArgumentException.class);
		Assertions.assertThrows(NestedServletException.class, () -> {
			mvc.perform(
					post("/api/sg/v1/orderItem").contentType(MediaType.APPLICATION_JSON).content(json.toJson(list)));
		});
	}

}
