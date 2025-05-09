package com.cognizant.Main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.Main.dto.CartDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.PlaceOrderDTO;
import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	 @GetMapping("/products")
		public ResponseEntity<List<ProductDTO>> getAllProducts()
		{
			List<ProductDTO> productDtoList = customerService.getAllProducts();
			return ResponseEntity.ok(productDtoList);
		}
	 
	 @GetMapping("/product/search/{title}")
		public ResponseEntity<List<ProductDTO>> searchProductByTitle(@PathVariable String title)
		{
			List<ProductDTO> productDtoList = customerService.searchProductByTitle(title);
			return ResponseEntity.ok(productDtoList);
		}


	     @PostMapping("/cart")
	     public ResponseEntity<?> postProductToCart(@RequestBody CartDTO cartDTO) {
	         System.out.println("Received CartDTO: " + cartDTO);
	         return customerService.addProducttoCart(cartDTO);
	     }
	     
	     @GetMapping("/cart/{userId}")
	     public ResponseEntity<OrderDTO> getCartByUserId(@PathVariable Long userId)
	     {
	    	 OrderDTO orderDTO = customerService.getCartByUserId(userId);
	    	 if(orderDTO==null)
	    	 {
	    		 return ResponseEntity.notFound().build();
	    	 }
	    	 return ResponseEntity.ok(orderDTO);
	     }
	     
	     @GetMapping("/cart/{userId}/deduct/{productId}")
	     public ResponseEntity<OrderDTO> addMinusOnProduct(@PathVariable Long userId,@PathVariable Long productId)
	     {
	    	 OrderDTO orderDTO = customerService.addMinusProduct(userId, productId);
	    	 return ResponseEntity.ok(orderDTO);
	     }
	 
	     @GetMapping("/cart/{userId}/add/{productId}")
	     public ResponseEntity<OrderDTO> addPlusOnProduct(@PathVariable Long userId,@PathVariable Long productId)
	     {
	    	 OrderDTO orderDTO = customerService.addPlusProduct(userId, productId);
	    	 return ResponseEntity.ok(orderDTO);
	     }
	     
	     @PostMapping("/placeOrder")
	     public ResponseEntity<OrderDTO> placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO) {
	         System.out.println("Received PlaceOrderDTO: " + placeOrderDTO); // Debugging statement
	         OrderDTO orderDTO = customerService.placeOrder(placeOrderDTO);
	         if (orderDTO == null) {
	             return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	         }
	         return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
	     }
	     
	     @GetMapping("/orders/{userId}")
	     public ResponseEntity<List<OrderDTO>> getOrderByUserId(@PathVariable Long userId)
	     {
	    	 List<OrderDTO> orderDTOList = customerService.getOrdersByUserId(userId);
	    	 return ResponseEntity.ok(orderDTOList);
	     }


	 
	 
}
