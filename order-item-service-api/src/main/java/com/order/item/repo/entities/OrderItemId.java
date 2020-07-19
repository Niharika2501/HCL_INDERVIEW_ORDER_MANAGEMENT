package com.order.item.repo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderItemId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NonNull
	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId;
	
	@NonNull
	@Column(name = "PRODUCT_CODE", nullable = false)
	private String productCode;
}