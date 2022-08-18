package com.example.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.oms.Entity.Customer;
@Repository
public interface OrderRepository extends JpaRepository<Customer, String> {

	boolean existsByCustomerId(String customerId);

//	@Query("from Order as u where u.productName LIKE %:str% or u.customerName LIKE %:str% or u.orderId LIKE %:str%")
//	List<Customer> findByNameLike(@Param("str") String str);

}
