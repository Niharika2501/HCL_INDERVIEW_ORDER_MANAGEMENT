package com.order.repo.entities;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long orderId;
	
	@NotNull
	private String productCode;
	
	@NotNull
	private String productName;
	
	@NotNull
	private Long quantity;
}