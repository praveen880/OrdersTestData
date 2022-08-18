package com.example.oms.repository;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.oms.Entity.Order;

@Repository
public interface OrdersTestDataRepo extends JpaRepository<Order, Integer> {

	boolean existsByOrderId(String customerId);
	
}
