package com.cognizant.Main.service.admin;

import java.util.List;

import com.cognizant.Main.dto.CategoryDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.entities.Category;
import com.cognizant.Main.entities.Product;

public interface AdminService {

	Category createdCategory(CategoryDTO categoryDTO);

	List<CategoryDTO> getAllCategories();

	Product postProduct(Long categoryId, ProductDTO productDTO);
	
	List<ProductDTO> getAllProducts();
	
	void deleteproduct(Long id);
	
	ProductDTO getProductById(Long id);
	
	ProductDTO UpdateProduct(Long categoryId,Long productId,ProductDTO productDTO);
	
	List<OrderDTO>getAllOrders();

}
