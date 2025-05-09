package com.cognizant.Main.service.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cognizant.Main.dto.CartDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.PlaceOrderDTO;
import com.cognizant.Main.dto.ProductDTO;

public interface CustomerService {

	List<ProductDTO> getAllProducts();
	List<ProductDTO> searchProductByTitle(String title);
	ResponseEntity<?> addProducttoCart(CartDTO cartDTO);
	OrderDTO getCartByUserId(Long userId);
//	OrderDTO addMinusProduct(Long userId);
	OrderDTO addMinusProduct(Long userId, Long productId);
	OrderDTO addPlusProduct(Long userId, Long productId);
	OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO);
	List<OrderDTO> getOrdersByUserId(Long userId);
}
