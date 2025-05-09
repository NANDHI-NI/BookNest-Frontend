package com.cognizant.Main.service.admin;

//import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.Main.dto.CategoryDTO;
import com.cognizant.Main.dto.OrderDTO;
import com.cognizant.Main.dto.ProductDTO;
import com.cognizant.Main.entities.Category;
import com.cognizant.Main.entities.Order;
//import com.cognizant.Main.entities.Order;
import com.cognizant.Main.entities.Product;
import com.cognizant.Main.enums.OrderStatus;
import com.cognizant.Main.repository.CategoryRepository;
import com.cognizant.Main.repository.OrderRepository;
import com.cognizant.Main.repository.ProductRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private CategoryRepository categoryReository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Category createdCategory(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category.setDescription(categoryDTO.getDescription());
		return categoryReository.save(category);
	}
	
	@Override
	public List<CategoryDTO> getAllCategories()
	{
		return categoryReository.findAll().stream().map(Category::getCategoryDTO).collect(Collectors.toList());
	}
	
	@Override
	public Product postProduct(Long categoryId, ProductDTO productDTO) {
	    Optional<Category> optionalCategory = categoryReository.findById(categoryId);
	    if (optionalCategory.isPresent()) {
	        Product product = new Product();
	        product.setName(productDTO.getName());
	        product.setPrice(productDTO.getPrice());
	        product.setDescription(productDTO.getDescription());
	        product.setCategory(optionalCategory.get());
	        return productRepository.save(product);
	    }
	    return null;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(Product::getProductDTO).collect(Collectors.toList());
	}

	@Override
	public void deleteproduct(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isEmpty())
		{
			throw new IllegalArgumentException("Product with id"+ id +" not found");
		}
		productRepository.deleteById(id);
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);
		if(optionalProduct.isPresent())
		{
			Product product = optionalProduct.get();
			return product.getProductDTO();
		}
		return null;
	}

	@Override
	public ProductDTO UpdateProduct(Long categoryId, Long productId, ProductDTO productDTO) {
		Optional<Category> optionalCategory = categoryReository.findById(categoryId);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if(optionalCategory.isPresent() && optionalProduct.isPresent())
		{
			Product product = optionalProduct.get();
			product.setName(productDTO.getName());
			product.setDescription(product.getDescription());
			product.setPrice(productDTO.getPrice());
			product.setCategory(optionalCategory.get());
//			if(productDTO.getImage()!=null)
//			{
//				product.setImage(productDTO.getImage().getBytes());
//			}
			Product updatedProduct = productRepository.save(product);
			ProductDTO UpdatedproductDTO = new ProductDTO();
			UpdatedproductDTO.setId(updatedProduct.getId());
		}
		return null;
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		// TODO Auto-generated method stub
		return orderRepository.findAllByOrderStatus(OrderStatus.SUBMITTED).stream().map(Order::getOrderDTO).collect(Collectors.toList());
	}

}
