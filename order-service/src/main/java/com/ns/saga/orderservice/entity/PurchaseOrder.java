package com.ns.saga.orderservice.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ns.saga.commondtos.event.OrderStatus;
import com.ns.saga.commondtos.event.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PURCHASE_ORDER_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer userId;
	private Integer productId;
	private Integer price;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

}
