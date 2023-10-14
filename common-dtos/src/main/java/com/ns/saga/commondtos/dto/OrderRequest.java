package com.ns.saga.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	
	private Integer userId;
	private Integer productId;
	private Integer amount;
	private Integer orderId;

}
