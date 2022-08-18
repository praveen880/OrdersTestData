package com.example.oms.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orders_testdata")
public class Order {
 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String orderId;
	@Column
	private String customerName;
	@Column
	private String address;
	@Column
	private String productName;
	@Column
	private String prodId;
	@Column
	private int quantity;
	@Column
	private String paymentType;

	public Order(String orderId, String customerName, String address, String productName, String prodId,
			int quantity, String paymentType) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.address = address;
		this.productName = productName;
		this.prodId = prodId;
		this.quantity = quantity;
		this.paymentType = paymentType;
	}
	
//	public Order(String customerName, String address, String productName, String prodId,
//			String quantity, String paymentType) {
//		this.customerName = customerName;
//		this.address = address;
//		this.productName = productName;
//		this.prodId = prodId;
//		this.quantity = quantity;
//		this.paymentType = paymentType;
//	}
	
	public Order() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderId=" + orderId + ", customerName=" + customerName + ", address=" + address
				+ ", productName=" + productName + ", prodId=" + prodId + ", quantity=" + quantity + ", paymentType="
				+ paymentType + "]";
	}

}
