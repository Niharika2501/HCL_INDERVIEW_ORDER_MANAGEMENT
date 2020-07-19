package com.order.item.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@ToString
@Validated
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotNull(message = "OrderId must be not blank")
	@NotNull
	private Long orderId;

	@NotNull(message = "product Code must be not blank")
	@NotNull
	private String productCode;

	@NotNull(message = "Product Name must be not blank")
	@NotNull
	private String productName;
	
	@NotNull
	private Long quantity;
}
