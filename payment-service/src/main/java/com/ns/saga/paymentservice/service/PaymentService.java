package com.ns.saga.paymentservice.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ns.saga.commondtos.dto.OrderRequest;
import com.ns.saga.commondtos.dto.PaymentRequest;
import com.ns.saga.commondtos.event.OrderEvent;
import com.ns.saga.commondtos.event.PaymentEvent;
import com.ns.saga.commondtos.event.PaymentStatus;
import com.ns.saga.paymentservice.entity.UserBalance;
import com.ns.saga.paymentservice.entity.UserTransaction;
import com.ns.saga.paymentservice.repository.UserBalanceRepository;
import com.ns.saga.paymentservice.repository.UserTransactionRepository;

@Service
public class PaymentService {

	@Autowired
	private UserBalanceRepository userBalanceRepository;

	@Autowired
	private UserTransactionRepository userTransactionRepository;

	/*
	 * // get the user id
		// check the balance availability
		// if balance sufficient -> payment completed and deduct amount in db
		// if payment not sufficient -> cancel order event and update the amount in db
	 */
	
	@Transactional
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequest orderRequest = orderEvent.getOrderRequest();
		PaymentRequest paymentRequest = new PaymentRequest(orderRequest.getOrderId(), orderRequest.getUserId(), orderRequest.getAmount());
		
		return userBalanceRepository.findById(orderRequest.getUserId())
		.filter(userBalance -> userBalance.getPrice() > orderRequest.getAmount())
		.map(userBalance -> {
			userBalance.setPrice(userBalance.getPrice() - orderRequest.getAmount());
			userTransactionRepository.save(new UserTransaction(orderRequest.getOrderId(), orderRequest.getUserId(), orderRequest.getAmount()));
			return new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_COMPLETED);
		}).orElse(new PaymentEvent(paymentRequest, PaymentStatus.PAYMENT_FAILED));
		
	}

	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {
		userTransactionRepository.findById(orderEvent.getOrderRequest().getOrderId())
		.ifPresent(userTransaction -> {
			userTransactionRepository.delete(userTransaction);
			userTransactionRepository.findById(userTransaction.getUserId())
			.ifPresent(userBalance -> userBalance.setAmount(userBalance.getAmount() + userTransaction.getAmount()));
		});
	}

	@PostConstruct
	public void initUserBalanceInDb() {
		userBalanceRepository
				.saveAll(Stream.of(
						new UserBalance(101, 5000),
						new UserBalance(102, 3000),
						new UserBalance(103, 4200),
						new UserBalance(104, 20000),
						new UserBalance(105, 999)
						).collect(Collectors.toList()));
	}

}
