package com.ns.saga.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ns.saga.paymentservice.entity.UserTransaction;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer> {

}
