package com.ns.saga.orderservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ns.saga.commondtos.dto.OrderRequest;
import com.ns.saga.commondtos.event.OrderStatus;
import com.ns.saga.orderservice.entity.PurchaseOrder;
import com.ns.saga.orderservice.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderStatusPublisher orderStatusPublisher;

	@Transactional
	public PurchaseOrder createOrder(OrderRequest orderRequest) {
		PurchaseOrder order = orderRepository.save(convertDtoToEntity(orderRequest));
		orderRequest.setOrderId(order.getId());
		// produce kafka event with status ORDER_CREATED
		orderStatusPublisher.publishOrderEvent(orderRequest, OrderStatus.ORDER_CREATED);
		return order;
	}
	
	public List<PurchaseOrder> getAllOrders() {
		return orderRepository.findAll();
	}

	private PurchaseOrder convertDtoToEntity(OrderRequest orderRequest) {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setProductId(orderRequest.getProductId());
		purchaseOrder.setUserId(orderRequest.getUserId());
		purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
		purchaseOrder.setPrice(orderRequest.getAmount());
		return purchaseOrder;
	}

}
