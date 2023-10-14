package com.ns.saga.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ns.saga.commondtos.dto.OrderRequest;
import com.ns.saga.commondtos.event.OrderEvent;
import com.ns.saga.commondtos.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {
	
	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publishOrderEvent(OrderRequest orderRequest, OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequest, orderStatus);
		orderSinks.tryEmitNext(orderEvent);
	}

}
