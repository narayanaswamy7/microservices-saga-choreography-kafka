package com.ns.saga.orderservice.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ns.saga.commondtos.event.PaymentEvent;

@Configuration
public class EventConsumerConfig {
	
	@Autowired
	private OrderStatusUpdateHandler handler;
	
	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer() {
		// listen payment-event topic
		// will check payment status
		// if payment status completed -> complete the order
		// if payment status failed -> cancel the order
		return (payment) -> handler.updateOrder(payment.getPaymentRequest().getOrderId(), purchaseOrder -> {
			purchaseOrder.setPaymentStatus(payment.getPaymentStatus());
		});
	}

}
