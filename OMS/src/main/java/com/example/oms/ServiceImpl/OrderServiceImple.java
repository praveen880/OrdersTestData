package com.example.oms.ServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.oms.Entity.Customer;
import com.example.oms.Entity.Order;
import com.example.oms.Entity.OrdersTestDataClass;
import com.example.oms.repository.OrderRepository;
import com.example.oms.repository.OrdersTestDataRepo;
import com.example.oms.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;

@Service
public class OrderServiceImple implements OrderService {

	@Autowired
	OrderRepository orderRepo;
	@Autowired
	OrdersTestDataRepo orderTdRepo;
	
    ObjectMapper mapper = new ObjectMapper();


	@Override
	public ResponseEntity<String> saveOrder(Customer order) {

		try {
			if (order.getCustomerId() == null || order.getCustomerId().trim().isEmpty()) {
				return new ResponseEntity<String>("CustomerId cann't be empty", HttpStatus.OK);
			}

			boolean status = orderRepo.existsByCustomerId(order.getCustomerId());
			if (status) {
				return new ResponseEntity<String>("CustomerId is Already Existed", HttpStatus.OK);
			}

			orderRepo.save(order);

			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<List<Customer>> getOrdersList() {

		try {
			List<Customer> ordersList = orderRepo.findAll();

			return new ResponseEntity<List<Customer>>(ordersList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<List<Customer>>(new ArrayList<>(), HttpStatus.FORBIDDEN);
		}

	}
	
	@Override
	public String getOrdersListJson() {

		try {
			List<Customer> ordersList = orderRepo.findAll();
		    String json = mapper.writeValueAsString(ordersList);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@Override
	public ResponseEntity<List<Customer>> searchOrders(String str) {

		try {

			List<Customer> ordersList = null; // orderRepo.findByNameLike(str);

			return new ResponseEntity<List<Customer>>(ordersList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<List<Customer>>(new ArrayList<>(), HttpStatus.FORBIDDEN);
		}

	}

	@Override
	public ResponseEntity<Map<String, Object>> saveOrderByCsvFile(MultipartFile file) {

		Map<String, Object> resp = new HashMap<>();
		int savedCount = 0;

		try {

			if (file.isEmpty() && file.getContentType() != "text/csv") {
				resp.put("message", "Please select a CSV file to upload.");
				resp.put("error", "bad request");
				resp.put("saved", savedCount);
				resp.put("existed", "");
				return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
			}

			List<Customer> orders = csvToOrders(file.getInputStream());
			List<Customer> existed = new ArrayList<>();

			for (Customer or : orders) {
				if (orderRepo.existsByCustomerId(or.getCustomerId())) {
					existed.add(or);
				} else {
					orderRepo.save(or);
					savedCount++;
				}
			}
			resp.put("message", "Success");
			resp.put("savedCount", savedCount);
			resp.put("existed", existed.size());
			resp.put("total", orders.size());
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("message", "Failed to save data");
			resp.put("error", e.getLocalizedMessage());
			resp.put("savedCount", savedCount);
			resp.put("existed", "");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.FORBIDDEN);
		}
	}

	public static List<Customer> csvToOrders(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<Customer> orders = new ArrayList<Customer>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Customer order = new Customer(csvRecord.get("email"), csvRecord.get("first_name"),
						csvRecord.get("last_name"), csvRecord.get("birthDate"), csvRecord.get("superuser"),
						csvRecord.get("salutation"), csvRecord.get("academicTitle"));

				order.setCustomerId(String.valueOf(genId()));
				orders.add(order);
			}
			return orders;
		} catch (IOException e) {
			throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
		}
	}

	public static int genId() {
		Random rnd = new Random();
	    int number = rnd.nextInt(9999999);
	    return number;
	}

	@Override
	public ResponseEntity<String> getOrdersDataAsCsv() {

		try {
			List<Customer> orders = orderRepo.findAll();

			String outputFile = new File("src\\main\\resources").getAbsolutePath() + "\\orders.csv";
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
			CSVPrinter csvFilePrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("customerId", "first_name",
					"last_name", "birthDate", "superuser", "salutation", "academicTitle", "email"));

			for (Customer or : orders) {
				csvFilePrinter.printRecord(or.getCustomerId(), or.getFirstName(), or.getLastName(), or.getBirthDate(),
						or.getSuperuser(), or.getSalutation(), or.getAcademicTitle(), or.getEmail());
			}
			csvFilePrinter.flush();
			csvFilePrinter.close();

			File file = new File(outputFile);
			FileInputStream fl = new FileInputStream(file);
			byte[] arr = new byte[(int) file.length()];
			fl.read(arr);
			fl.close();
			String data = Base64.getEncoder().encodeToString(arr);
			return new ResponseEntity<String>(data, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.FORBIDDEN);
		}

	}

	public ResponseEntity<Map<String, Object>> saveOrderByCsvFileUsingCMD(String path) {

		Map<String, Object> resp = new HashMap<>();
		int savedCount = 0;

		try {
			System.out.println("Path ====> "+path);
			InputStream is = new FileInputStream(path);

			List<Customer> orders = csvToOrders(is);
			List<Customer> existed = new ArrayList<>();

			for (Customer or : orders) {
				if (orderRepo.existsByCustomerId(or.getCustomerId())) {
					existed.add(or);
				} else {
					orderRepo.save(or);
					savedCount++;
				}
			}
//			saveCsvFile(orders);
			resp.put("message", "Success");
			resp.put("savedCount", savedCount);
			resp.put("existed", existed.size());
			resp.put("total", orders.size());
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("message", "Failed to save data");
			resp.put("error", e.getLocalizedMessage());
			resp.put("savedCount", savedCount);
			resp.put("existed", "");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.FORBIDDEN);
		}

	}

	public void saveCsvFile(List<Customer> c) throws IOException {
		try {
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long time = timestamp.getTime();
			String fileName = "customers_"+time+".csv";
			String outputFile = new File("src\\main\\resources").getAbsolutePath() + "\\"+fileName;
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
			CSVPrinter csvFilePrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("customerId", "first_name",
					"last_name", "birthDate", "superuser", "salutation", "academicTitle", "email"));

			for (Customer or : c) {
				csvFilePrinter.printRecord(or.getCustomerId(), or.getFirstName(), or.getLastName(), or.getBirthDate(),
						or.getSuperuser(), or.getSalutation(), or.getAcademicTitle(), or.getEmail());
			}
			csvFilePrinter.flush();
			csvFilePrinter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readCsvFile(String filename) {
		try {

			String outputFile = new File("src\\main\\resources").getAbsolutePath() + "\\" + filename;
			File file = new File(outputFile);
			FileInputStream fl = new FileInputStream(file);
			byte[] arr = new byte[(int) file.length()];
			fl.read(arr);
			fl.close();
			String data = Base64.getEncoder().encodeToString(arr);
	      //  String json = mapper.writeValueAsString(list);

			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	@Override
	public String[] getfilslist() {
		String outputFile = new File("src\\main\\resources").getAbsolutePath();
		File f = new File(outputFile);
		return f.list();
	}

	@Override
	public void getTestData(int i) {

		ExampleDataGenerator<Order> generator = new ExampleDataGenerator<>(Order.class, LocalDateTime.now());
        generator.setData(Order::setOrderId, DataType.ZIP_CODE);
        generator.setData(Order::setCustomerName, DataType.FULL_NAME);
        generator.setData(Order::setAddress, DataType.ADDRESS);
        generator.setData(Order::setProductName, DataType.FULL_NAME);
        generator.setData(Order::setProdId, DataType.ZIP_CODE);
        generator.setData(Order::setQuantity, DataType.NUMBER_UP_TO_10);
        generator.setData(Order::setPaymentType, DataType.TRANSACTION_STATUS);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Order> data = generator.create(i, new Random().nextInt());
        stopWatch.stop();
        ordersTOCsv(data);
	    try {
			String json = mapper.writeValueAsString(data);
            System.out.println("======="+json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	
	@Override
	public ResponseEntity<List<Order>> getOrdersList1() {

		try {
			List<Order> ordersList = orderTdRepo.findAll();

			return new ResponseEntity<List<Order>>(ordersList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<List<Order>>(new ArrayList<>(), HttpStatus.FORBIDDEN);
		}

	}
	
	@Override
	public String getOrdersListJson1() {

		try {
			List<Order> ordersList = orderTdRepo.findAll();
		    String json = mapper.writeValueAsString(ordersList);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}
	
	@Override
	public ResponseEntity<Map<String, Object>> saveOrderByCsvFileUsingCMD1(String path) {

		Map<String, Object> resp = new HashMap<>();
		int savedCount = 0;

		try {
			System.out.println("Path ====> "+path);
			InputStream is = new FileInputStream(path);

			List<Order> orders = csvToOrders1(is);
			List<Order> existed = new ArrayList<>();

			for (Order or : orders) {
				if (orderTdRepo.existsByOrderId(or.getOrderId())) {
					existed.add(or);
				} else {
					orderTdRepo.save(or);
					savedCount++;
				}
			}
//			saveCsvFile(orders);
			resp.put("message", "Success");
			resp.put("savedCount", savedCount);
			resp.put("existed", existed.size());
			resp.put("total", orders.size());
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			resp.put("message", "Failed to save data");
			resp.put("error", e.getLocalizedMessage());
			resp.put("savedCount", savedCount);
			resp.put("existed", "");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.FORBIDDEN);
		}

	}

	public static List<Order> csvToOrders1(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			List<Order> orders = new ArrayList<Order>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Order order = new Order(csvRecord.get("orderId"), csvRecord.get("customerName"),
						csvRecord.get("address"), csvRecord.get("productName"), csvRecord.get("prodId"),
						Integer.parseInt(csvRecord.get("quantity")), csvRecord.get("paymentType"));

				orders.add(order);
			}
			return orders;
		} catch (IOException e) {
			throw new RuntimeException("failed to parse CSV file: " + e.getMessage());
		}
		
	}
	
	@Override
	public void stringToOrders(String str) {
		try {
			System.out.println("====> "+str);
			List<Order> orders = mapper.readValue(str, new TypeReference<List<Order>>(){});
			orderTdRepo.saveAll(orders);
			System.out.println("Successfully Saved into Database");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	
	public void ordersTOCsv(List<Order> orders) {

		try {

			String outputFile ="D:\\orders5.csv";
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile));
			CSVPrinter csvFilePrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("orderId", "customerName",
					"address", "productName", "prodId", "quantity", "paymentType"));

			for (Order or : orders) {
				csvFilePrinter.printRecord(or.getOrderId(), or.getCustomerName(), or.getAddress(), or.getProductName(),
						or.getProdId(), or.getQuantity(), or.getPaymentType());
			}
			csvFilePrinter.flush();
			csvFilePrinter.close();
            System.out.println("File Saved at "+outputFile);
			File file = new File(outputFile);
			FileInputStream fl = new FileInputStream(file);
			byte[] arr = new byte[(int) file.length()];
			fl.read(arr);
			fl.close();
			String data = Base64.getEncoder().encodeToString(arr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	
	
}
