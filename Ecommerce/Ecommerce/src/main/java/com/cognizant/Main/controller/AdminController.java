package com.cognizant.Main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.Main.dto.CategoryDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.entities.Category;
import com.cognizant.Main.entities.Product;
import com.cognizant.Main.service.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


	@Autowired
	private AdminService adminService;
	
	@PostMapping("/category")
	public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO)
	{
	   Category createdcategory = adminService.createdCategory(categoryDTO);	
	   return ResponseEntity.status(HttpStatus.CREATED).body(createdcategory);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDTO>> getAllCategories()
	{
		List<CategoryDTO> allCategories = adminService.getAllCategories();
		return ResponseEntity.ok(allCategories);
	}
	
	@PostMapping("/product/{categoryId}")
	public ResponseEntity<Product> postProduct(@PathVariable Long categoryId, @RequestBody ProductDTO productDTO) {
	    Product postedProduct = adminService.postProduct(categoryId, productDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body(postedProduct);
	}

    @GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts()
	{
		List<ProductDTO> productDtoList = adminService.getAllProducts();
		return ResponseEntity.ok(productDtoList);
	}
    
    @DeleteMapping("/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id)
    {
    	adminService.deleteproduct(id);
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id)
    {
    	ProductDTO productDTO = adminService.getProductById(id);
    	if(productDTO==null)
    	{
    		return ResponseEntity.notFound().build();
    	}
    	return ResponseEntity.ok(productDTO);
    }
    
    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long categoryId,@PathVariable Long productId,@ModelAttribute ProductDTO productDTO)
    {
    	ProductDTO updateProduct = adminService.UpdateProduct(categoryId, productId, productDTO);
    	if(updateProduct==null)
    	{
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
    	}
    	return ResponseEntity.ok(updateProduct);
    }
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders()
    {
    	List<OrderDTO> orderDTOList = adminService.getAllOrders();
    	return ResponseEntity.ok(orderDTOList);
    }
}
