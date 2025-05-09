package com.cognizant.Main.service.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.Main.dto.CartDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.PlaceOrderDTO;
import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.entities.CartItems;
import com.cognizant.Main.entities.Order;
import com.cognizant.Main.entities.Product;
import com.cognizant.Main.entities.User;
import com.cognizant.Main.enums.OrderStatus;
import com.cognizant.Main.repository.CartItemsRepository;
import com.cognizant.Main.repository.OrderRepository;
import com.cognizant.Main.repository.ProductRepository;
import com.cognizant.Main.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	@Autowired
    private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private CartItemsRepository cartItemsRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<ProductDTO> getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll().stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public List<ProductDTO> searchProductByTitle(String title) {
		// TODO Auto-generated method stub
		return productRepository.findAllByNameContaining(title).stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> addProducttoCart(CartDTO cartDTO) {
	    System.out.println("Received CartDTO: " + cartDTO);

	    Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(cartDTO.getUserId(), OrderStatus.PENDING);
	    
	    // Check if pendingOrder is null and create a new one if necessary
	    if (pendingOrder == null) {
	        pendingOrder = new Order();
	        pendingOrder.setUser(userRepository.findById(cartDTO.getUserId()).orElse(null));
	        pendingOrder.setOrderStatus(OrderStatus.PENDING);
	        pendingOrder.setPrice(0L); // Initialize price to 0
	        pendingOrder.setDate(new Date()); // Set the order date
	        pendingOrder.setCartItems(new ArrayList<>()); // Initialize cartItems list
	        pendingOrder = orderRepository.save(pendingOrder);
	    }

	    Optional<CartItems> optionalCartItem = cartItemsRepository.findByUserIdAndProductIdAndOrderId(cartDTO.getUserId(), cartDTO.getProductId(), pendingOrder.getId());
	    if (optionalCartItem.isPresent()) {
	        CartItems existingCartItem = optionalCartItem.get();
	        existingCartItem.setQuantity(existingCartItem.getQuantity() + cartDTO.getQuantity());
	        existingCartItem.setPrice(existingCartItem.getPrice() + (cartDTO.getPrice() * cartDTO.getQuantity()));
	        CartItems updatedCartItem = cartItemsRepository.save(existingCartItem);
	        
	        // Update the order price
	        pendingOrder.setPrice(pendingOrder.getPrice() + (cartDTO.getPrice() * cartDTO.getQuantity()));
	        orderRepository.save(pendingOrder);
	        
	        // Map to CartDTO
	        CartDTO updatedCartItemDTO = new CartDTO();
	        updatedCartItemDTO.setId(updatedCartItem.getId());
	        updatedCartItemDTO.setPrice(updatedCartItem.getPrice());
	        updatedCartItemDTO.setQuantity(updatedCartItem.getQuantity());
	        updatedCartItemDTO.setProductId(updatedCartItem.getProduct().getId());
	        updatedCartItemDTO.setOrderId(pendingOrder.getId());
	        updatedCartItemDTO.setProductName(updatedCartItem.getProduct().getName());
	        updatedCartItemDTO.setUserId(updatedCartItem.getUser().getId());

	        return ResponseEntity.status(HttpStatus.OK).body(updatedCartItemDTO);
	    } else {
	        Optional<Product> optionalProduct = productRepository.findById(cartDTO.getProductId());
	        Optional<User> optionalUser = userRepository.findById(cartDTO.getUserId());
	        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
	            Product product = optionalProduct.get();
	            CartItems cartItems = new CartItems();
	            cartItems.setProduct(product);
	            cartItems.setUser(optionalUser.get());
	            cartItems.setQuantity(cartDTO.getQuantity());
	            cartItems.setOrder(pendingOrder);
	            cartItems.setPrice(cartDTO.getPrice() + cartDTO.getQuantity());
	            
	            // Save and update
	            CartItems updatedCart = cartItemsRepository.save(cartItems);
	            pendingOrder.setPrice(pendingOrder.getPrice() + cartItems.getPrice());
	            pendingOrder.getCartItems().add(cartItems);
	            orderRepository.save(pendingOrder);
	            
	            // Map to CartDTO
	            CartDTO updatedCartItemDTO = new CartDTO();
	            updatedCartItemDTO.setId(updatedCart.getId());
	            updatedCartItemDTO.setPrice(updatedCart.getPrice());
	            updatedCartItemDTO.setQuantity(updatedCart.getQuantity());
	            updatedCartItemDTO.setProductId(updatedCart.getProduct().getId());
	            updatedCartItemDTO.setOrderId(pendingOrder.getId());
	            updatedCartItemDTO.setProductName(product.getName());
	            updatedCartItemDTO.setUserId(optionalUser.get().getId());

	            System.out.println("Updated CartDTO: " + updatedCartItemDTO);
	            
	            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItemDTO);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
	        }
	    }
	}

	@Override
	public OrderDTO getCartByUserId(Long userId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		List<CartDTO> cartDTOList = pendingOrder.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList());
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setCartDTO(cartDTOList);
		orderDTO.setAmount(pendingOrder.getPrice());
		orderDTO.setId(pendingOrder.getId());
		orderDTO.setOrderStatus(pendingOrder.getOrderStatus());
		return orderDTO;
	}

	@Override
	public OrderDTO addMinusProduct(Long userId,Long productId) {
		Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
	    Optional<Product> optionalProduct = productRepository.findById(productId);
	    Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
	    
	    if (optionalCartItems.isPresent() && optionalProduct.isPresent()) {
	        CartItems cartItem = optionalCartItems.get();
	        cartItem.setQuantity(cartItem.getQuantity() - 1);
	        pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice()); // Add product price to order
	        
	        cartItemsRepository.save(cartItem);
	        orderRepository.save(pendingOrder);

	        // Convert Order to OrderDTO
	        OrderDTO orderDTO = new OrderDTO();
	        orderDTO.setId(pendingOrder.getId());
	        orderDTO.setOrderDescription(pendingOrder.getDescription());
	        orderDTO.setDate(pendingOrder.getDate());
	        orderDTO.setAmount(pendingOrder.getPrice());
	        orderDTO.setAddress(pendingOrder.getAddress());
	        orderDTO.setOrderStatus(pendingOrder.getOrderStatus());
	        orderDTO.setPaymentType(pendingOrder.getPaymentType());
	        orderDTO.setUsername(pendingOrder.getUser().getName());

	        // Map CartItems to CartDTO
	        List<CartDTO> cartDTOList = pendingOrder.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList());
	        orderDTO.setCartDTO(cartDTOList);

	        return orderDTO;
	    }

	    return null;
		
	}

	@Override
	public OrderDTO addPlusProduct(Long userId, Long productId) {
	    Order pendingOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
	    Optional<Product> optionalProduct = productRepository.findById(productId);
	    Optional<CartItems> optionalCartItems = cartItemsRepository.findByUserIdAndProductIdAndOrderId(userId, productId, pendingOrder.getId());
	    
	    if (optionalCartItems.isPresent() && optionalProduct.isPresent()) {
	        CartItems cartItem = optionalCartItems.get();
	        cartItem.setQuantity(cartItem.getQuantity() + 1);
	        pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice()); // Add product price to order
	        
	        cartItemsRepository.save(cartItem);
	        orderRepository.save(pendingOrder);

	        // Convert Order to OrderDTO
	        OrderDTO orderDTO = new OrderDTO();
	        orderDTO.setId(pendingOrder.getId());
	        orderDTO.setOrderDescription(pendingOrder.getDescription());
	        orderDTO.setDate(pendingOrder.getDate());
	        orderDTO.setAmount(pendingOrder.getPrice());
	        orderDTO.setAddress(pendingOrder.getAddress());
	        orderDTO.setOrderStatus(pendingOrder.getOrderStatus());
	        orderDTO.setPaymentType(pendingOrder.getPaymentType());
	        orderDTO.setUsername(pendingOrder.getUser().getName());

	        // Map CartItems to CartDTO
	        List<CartDTO> cartDTOList = pendingOrder.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList());
	        orderDTO.setCartDTO(cartDTOList);

	        return orderDTO;
	    }

	    return null;
	}

	@Override
	public OrderDTO placeOrder(PlaceOrderDTO placeOrderDTO) {
	    Order existingOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDTO.getUserId(), OrderStatus.PENDING);
	    Optional<User> optionalUser = userRepository.findById(placeOrderDTO.getUserId());

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();

	        if (existingOrder != null) {
	            existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
	            existingOrder.setAddress(placeOrderDTO.getAddress());
	            existingOrder.setDate(new Date());
	            existingOrder.setPaymentType(placeOrderDTO.getPayment());
	            existingOrder.setDescription(placeOrderDTO.getOrderDescription());
	            existingOrder.setPrice(existingOrder.getPrice());
	            orderRepository.save(existingOrder);
	        } else {
	            // Handle case where no pending order exists for this user
	            existingOrder = new Order();
	            existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
	            existingOrder.setUser(user);
	            existingOrder.setPrice(0L);
	            existingOrder.setAddress(placeOrderDTO.getAddress());
	            existingOrder.setDate(new Date());
	            existingOrder.setPaymentType(placeOrderDTO.getPayment());
	            existingOrder.setDescription(placeOrderDTO.getOrderDescription());
	            orderRepository.save(existingOrder);
	        }

	        // Create OrderDTO and populate cartDTO
	        OrderDTO orderDTO = existingOrder.getOrderDTO();
	        orderDTO.setOrder(existingOrder);
	        return orderDTO; // Return the saved order details
	    }
	    return null; // You may want to return something more meaningful here
	}


	// Method to convert Order to OrderDTO
	@SuppressWarnings("unused")
	private OrderDTO convertOrderToOrderDTO(Order order) {
	    OrderDTO orderDTO = new OrderDTO();
	    orderDTO.setAddress(order.getAddress());
	    orderDTO.setAmount(order.getPrice());
	    orderDTO.setDate(order.getDate());
	    orderDTO.setId(order.getId());
	    orderDTO.setOrderDescription(order.getDescription());
	    orderDTO.setOrderStatus(order.getOrderStatus());
	    orderDTO.setPaymentType(order.getPaymentType());
	    orderDTO.setUsername(order.getUser().getName());
	    orderDTO.setOrder(order);
	    
	    // Populate cartDTO
	    List<CartDTO> cartDTOList = order.getCartItems().stream().map(CartItems::getCartDTO).collect(Collectors.toList());
	    orderDTO.setCartDTO(cartDTOList);
	    
	    return orderDTO;
	}


	@Override
	public List<OrderDTO> getOrdersByUserId(Long userId) {
		
		return orderRepository.findAllByUserIdAndOrderStatus(userId, OrderStatus.SUBMITTED).stream().map(Order::getOrderDTO).collect(Collectors.toList());
	}




}
