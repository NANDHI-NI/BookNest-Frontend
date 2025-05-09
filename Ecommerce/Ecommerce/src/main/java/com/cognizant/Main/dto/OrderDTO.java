package com.cognizant.Main.dto;

import java.util.Date;
import java.util.List;

import com.cognizant.Main.entities.Order;
import com.cognizant.Main.enums.OrderStatus;

public class OrderDTO {

	private String orderDescription;
	private List<CartDTO> cartDTO;
	private Long id;
	private Date date;
	private Long amount;
	private String address;
	private OrderStatus orderStatus;
	private String paymentType;
	private String username;
	private Order order;
//	public OrderDTO(Order existingOrder) {
//		// TODO Auto-generated constructor stub
//		this.setOrder(existingOrder);
//	}
	public String getOrderDescription() {
		return orderDescription;
	}
	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}
	public List<CartDTO> getCartDTO() {
		return cartDTO;
	}
	public void setCartDTO(List<CartDTO> cartDTO) {
		this.cartDTO = cartDTO;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
