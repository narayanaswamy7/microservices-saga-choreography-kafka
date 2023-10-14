package com.ns.saga.commondtos.event;

import java.util.Date;
import java.util.UUID;

import com.ns.saga.commondtos.dto.PaymentRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event {

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = new Date();
	private PaymentRequest paymentRequest;
	private PaymentStatus paymentStatus;

	public PaymentEvent(PaymentRequest paymentRequest, PaymentStatus paymentStatus) {
		this.paymentRequest = paymentRequest;
		this.paymentStatus = paymentStatus;
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
