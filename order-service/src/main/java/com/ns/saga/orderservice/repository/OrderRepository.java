package com.ns.saga.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ns.saga.orderservice.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer> {

}
