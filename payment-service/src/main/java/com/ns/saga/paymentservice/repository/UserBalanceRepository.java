package com.ns.saga.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ns.saga.paymentservice.entity.UserBalance;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer> {

}
