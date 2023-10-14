package com.ns.saga.commondtos.dto;

import com.ns.saga.commondtos.event.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	private Integer userId;
	private Integer productId;
	private Integer amount;
	private Integer orderId;
	private OrderStatus orderStatus;
	

}
