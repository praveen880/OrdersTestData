package com.example.oms;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.oms.Entity.OrdersTestDataClass;
import com.example.oms.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

	@Autowired
	OrderService orderService;
	
	public void callService(String args[]){
		if (args.length > 0) {
			System.out.println("==========================");
			String cmd= !args[0].equals("--spring.output.ansi.enabled=always")? args[0] : "goBack";
			if(!cmd.equals("goBack") && cmd.equals("uplaodCsv")) {
				System.out.println("Entered to write data using csv");
				System.out.println(orderService.saveOrderByCsvFileUsingCMD1(args[1]));
			}
			if(!cmd.equals("goBack") && cmd.equals("readFile")){
				System.out.println("Entered to read csv data");
				System.out.println(orderService.readCsvFile(args[1]));
			}
			if(!cmd.equals("goBack") && cmd.equals("ls")) {
				System.out.println("Entered to list files");
				String arr[] = orderService.getfilslist();
				for(String a:arr) {
					System.out.println(a);
				}
			}
			if(!cmd.equals("goBack") && cmd.equals("dataList")) {
				System.out.println("Entered to read data");
				System.out.println(orderService.getOrdersListJson1());
			}
			if(!cmd.equals("goBack") && cmd.equals("generateTestData")) {
				System.out.println("Entered to generate test data");
				orderService.getTestData(Integer.parseInt(args[1]));
			}
			if(!cmd.equals("goBack") && cmd.equals("save")) {
				System.out.println("Entered to save data");
				orderService.stringToOrders(args[1]);
			}
			System.out.println("==========================");
		}
	}
	
	@Override
	public void run(String... args) throws Exception {
		callService(args);
	}

}
