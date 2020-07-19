package com.order.item.repo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "ORDER_ITEM")
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemId orderItemId;
	
	@NonNull
	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;
	
	@NonNull
	@Column(name = "QUANTITY", nullable = false)
	private Long quantity;
}