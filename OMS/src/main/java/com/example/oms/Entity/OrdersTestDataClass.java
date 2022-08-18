package com.example.oms.Entity;

import java.util.UUID;

public class OrdersTestDataClass {

		private UUID orderId;
		private String customerName;
		private String address;
		private String productName;
		private UUID prodId;
		private int quantity;
		private String paymentType;
		
		public OrdersTestDataClass() {}
		
		public UUID getOrderId() {
			return orderId;
		}
		public void setOrderId(UUID orderId) {
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
		public UUID getProdId() {
			return prodId;
		}
		public void setProdId(UUID prodId) {
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
}
