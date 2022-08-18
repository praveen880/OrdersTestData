package com.example.oms.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.oms.Entity.Customer;
import com.example.oms.Entity.Order;

public interface OrderService {

	ResponseEntity<String> saveOrder(Customer user);
	
    ResponseEntity<List<Customer>>  getOrdersList();

	ResponseEntity<List<Customer>> searchOrders(String str);

	ResponseEntity<Map<String,Object>> saveOrderByCsvFile(MultipartFile file);

	ResponseEntity<String> getOrdersDataAsCsv();

	ResponseEntity<Map<String, Object>> saveOrderByCsvFileUsingCMD(String path);

	String readCsvFile(String filename);

	String[] getfilslist();

	String getOrdersListJson();

	void getTestData(int i);

	ResponseEntity<List<Order>> getOrdersList1();

	String getOrdersListJson1();

	ResponseEntity<Map<String, Object>> saveOrderByCsvFileUsingCMD1(String path);

	void stringToOrders(String str);
    	

}
