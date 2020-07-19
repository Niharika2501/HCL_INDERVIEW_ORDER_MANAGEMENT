package com.order.item.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.order.item.repo.entities.OrderItem;
import com.order.item.repo.entities.OrderItemId;

public interface OrderItemRepository extends CrudRepository<OrderItem, OrderItemId> {
	
	List<OrderItem> findByOrderItemIdOrderId(Long orderId);
}