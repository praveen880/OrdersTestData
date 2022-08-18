package com.example.oms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OmsApplicationTests {
	
//	public static String customerName = null;
//	public static String address = null;
//	public static String productName = null;
//	public static String prodId = null;
//	public static String quantity = null;
//	public static String paymentType = null;
//
//	@Test
//	public void contextLoads() throws Exception {
//		WebDriver d = null;	
//		
//		
//		
//		try {
//			System.setProperty("webdriver.chrome.driver","src\\driver\\chromedriver.exe");
//			d= new ChromeDriver();
//			String urlLocation= new File("src\\main\\resources").getAbsolutePath() + "\\" + "OMSUI.html";
//			
//			d.get(urlLocation);
//			
//			readCSV();
////			d.findElement(By.id("orderId")).sendKeys(String.valueOf (gen()));
//			d.findElement(By.id("customerName")).sendKeys(customerName);
//			d.findElement(By.id("address")).sendKeys(address);
//			d.findElement(By.id("productName")).sendKeys(productName);
//			d.findElement(By.id("prodId")).sendKeys(prodId);
//			d.findElement(By.id("quantity")).sendKeys(quantity);
//			d.findElement(By.id("paymentType")).sendKeys(paymentType);
//			d.findElement(By.xpath("/html/body/div/div[1]/div[2]/form/div/div[8]/button[2]"));
//			String u = d.getCurrentUrl();
//			String fileLocation = new File("").getAbsolutePath() + "\\" + "OMSUI.html";
//			System.out.println(fileLocation);
//			if(u.equals(fileLocation))
//			{
//				System.out.println("Test Case Passed");
//			}		else
//			{
//				System.out.println("Test Case Failed");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally {
////			d.close();
//		}
//	}
//	public void  readCSV ()throws Exception{
//		String fileLocation = new File("src\\main\\resources").getAbsolutePath() + "\\" + "orders.csv";
//
//		File csvFile = new File(fileLocation);
//		BufferedReader br = new BufferedReader(new FileReader(csvFile));
//		String line = "";
//
//		try {
//			while ((line = br.readLine())!=null) {
//				String[] count = line.split(",");
//				customerName = count[0];
//				address= count[1];
//				productName = count[2];
//				prodId = count[3];
//				quantity = count[4];
//				paymentType= count[5];
//				System.out.println(customerName + " "+address+" "+productName+" "+prodId+" "+quantity+""+paymentType);
//			}
//			System.out.println("===done===");
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//
//		}
//	}
//	public int gen() {
//	    Random r = new Random( System.currentTimeMillis() );
//	    return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
//	}

}