package com.example.oms.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.oms.Entity.Customer;
import com.example.oms.Entity.Order;
import com.example.oms.service.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	OrderService userService;

	@PostMapping("/saveOrder")
	public ResponseEntity<String> saveOrder(@RequestBody Customer user) {
		
		return userService.saveOrder(user); 
	}
	
	@PostMapping("/saveOrderByCsvFile")
	public ResponseEntity<Map<String,Object>> saveOrderByCsvFile(@RequestParam(value="file") MultipartFile file) throws IOException {
		
  	return userService.saveOrderByCsvFile(file); 
	}
	
	@GetMapping("/getOrdersCsv")
	public ResponseEntity<String> getOrdersCsv() {
		
		return userService.getOrdersDataAsCsv(); 
	}
	
	@GetMapping("/getOrdersList")
	public ResponseEntity<List<Customer>> getUsersList() {
		
		return userService.getOrdersList(); 
	}
	
	
	@GetMapping("/searchOrders/{str}")
	public ResponseEntity<List<Customer>> searchUsers(@PathVariable("str") String str) {
		
		return userService.searchOrders(str); 
	}
	
	
}
