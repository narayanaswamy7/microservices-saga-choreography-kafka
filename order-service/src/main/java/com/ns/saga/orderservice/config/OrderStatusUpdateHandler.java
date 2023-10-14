package com.ns.saga.orderservice.config;

import java.util.function.Consumer;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.ns.saga.commondtos.dto.OrderRequest;
import com.ns.saga.commondtos.event.OrderStatus;
import com.ns.saga.commondtos.event.PaymentStatus;
import com.ns.saga.orderservice.entity.PurchaseOrder;
import com.ns.saga.orderservice.repository.OrderRepository;
import com.ns.saga.orderservice.service.OrderStatusPublisher;

@Configuration
public class OrderStatusUpdateHandler {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private OrderStatusPublisher publisher;

	@Transactional
	public void updateOrder(int id, Consumer<PurchaseOrder> consumer) {
		repository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
	}
	
	public OrderRequest convertEntityToDto(PurchaseOrder purchaseOrder) {
		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderId(purchaseOrder.getId());
		orderRequest.setUserId(purchaseOrder.getUserId());
		orderRequest.setAmount(purchaseOrder.getPrice());
		orderRequest.setProductId(purchaseOrder.getProductId());
		return orderRequest;
	}

	private void updateOrder(PurchaseOrder purchaseOrder) {
		boolean isPaymentComplete = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
		OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
		purchaseOrder.setOrderStatus(orderStatus);
		if(!isPaymentComplete) {
			publisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
		}
	}

}
