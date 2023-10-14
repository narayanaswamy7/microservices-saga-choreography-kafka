package com.ns.saga.commondtos.event;

import java.util.Date;
import java.util.UUID;

import com.ns.saga.commondtos.dto.OrderRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private OrderRequest orderRequest;
	private OrderStatus orderStatus;

	public OrderEvent(OrderRequest orderRequest, OrderStatus orderStatus) {
		this.orderRequest = orderRequest;
		this.orderStatus = orderStatus;
	}

	@Override
	public UUID getEventId() {
		return eventId;
	}

	@Override
	public Date getDate() {
		return eventDate;
	}

}
