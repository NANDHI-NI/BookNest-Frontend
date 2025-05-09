package com.cognizant.Main.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.cognizant.Main.entities.Order;
import com.cognizant.Main.enums.OrderStatus;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

	Order findByUserIdAndOrderStatus(Long userId, OrderStatus pending);

	List<Order> findAllByUserIdAndOrderStatus(Long userId, OrderStatus submitted);

	List<Order> findAllByOrderStatus(OrderStatus orderStatus);

}
