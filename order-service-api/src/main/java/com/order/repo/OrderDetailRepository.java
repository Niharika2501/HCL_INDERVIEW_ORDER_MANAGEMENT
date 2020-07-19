package com.order.repo;

import org.springframework.data.repository.CrudRepository;

import com.order.repo.entities.OrderDetail;

public interface OrderDetailRepository extends CrudRepository<OrderDetail, Long> {
	
}