package com.ns.saga.paymentservice.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ns.saga.commondtos.event.OrderEvent;
import com.ns.saga.commondtos.event.OrderStatus;
import com.ns.saga.commondtos.event.PaymentEvent;
import com.ns.saga.paymentservice.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentConsumerConfig {
	
	@Autowired
	private PaymentService paymentService;
	
	@Bean
	public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
		return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
	}
	
	private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
		// get the user id
		// check the balance availability
		// if balance sufficient -> payment completed and deduct amount in db
		// if payment not sufficient -> cancel order event and update the amount in db
		if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
			return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
		} else {
			return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
		}
	}

}
