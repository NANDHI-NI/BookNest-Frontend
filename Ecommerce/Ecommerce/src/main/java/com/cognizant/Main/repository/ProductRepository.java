package com.cognizant.Main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

	List<Product> findAllByNameContaining(String title);

}
