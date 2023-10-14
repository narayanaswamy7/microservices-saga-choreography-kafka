package com.ns.saga.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ns.saga.commondtos.dto.OrderRequest;
import com.ns.saga.orderservice.entity.PurchaseOrder;
import com.ns.saga.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.createOrder(orderRequest);
	}
	
	@GetMapping
	public List<PurchaseOrder> getOrders() {
		return orderService.getAllOrders();
	}

}
