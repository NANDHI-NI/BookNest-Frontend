package com.cognizant.Main.dto;

public class PlaceOrderDTO {
    private Long userId;
    private String address;
    private String orderDescription;
    private String payment;

    // Getters and setters
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOrderDescription() {
        return orderDescription;
    }
    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
}
