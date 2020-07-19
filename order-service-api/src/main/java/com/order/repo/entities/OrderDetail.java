package com.order.repo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Validated
@EqualsAndHashCode
@Table(name = "ORDER_DETAIL")
public class OrderDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NonNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID", nullable = false)
	private Long orderId;

	@NotNull(message="Customer Name must not be blank")
	@NotEmpty(message="Customer Name must not be Empty")
	@NonNull
	@Column(name = "CUSTOMER_NAME", nullable = false)
	private String customerName;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_DATE", nullable = false)
	private Date orderDate;

	@NotNull(message="Provide shipping address")
	@NonNull
	@Column(name = "SHIPPING_ADDRESS", nullable = false)
	private String shippingAddress;

	@NotNull(message="Total Amount must not be null")
	@NonNull
	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private Double totalAmount;
	
	@Transient
	private List<OrderItem> orderItems;
}