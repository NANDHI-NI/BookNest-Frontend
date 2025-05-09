package com.cognizant.Main.entities;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cognizant.Main.dto.CartDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;

@Entity
@Table(name="`orders`")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String address;
    private String paymentType;
    private Date date;
    private Long price;
    private OrderStatus orderStatus;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

//    @OneToMany(fetch=FetchType.LAZY, mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true)
//    private List<CartItems> cartItems = new ArrayList<>();

    // Default constructor
    public Order() {
        this.cartItems = new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems = new ArrayList<>();

    public List<CartItems> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
    public OrderDTO getOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setOrderStatus(orderStatus);
        orderDTO.setAmount(price);
        orderDTO.setAddress(address);
        orderDTO.setPaymentType(paymentType);
        orderDTO.setUsername(user.getName());
        orderDTO.setDate(date);
        orderDTO.setOrderDescription(description);

        // Populate cartDTO
        List<CartDTO> cartDTOList = cartItems.stream().map(CartItems::getCartDTO).collect(Collectors.toList());
        orderDTO.setCartDTO(cartDTOList);
        return orderDTO;
    }
   

}




