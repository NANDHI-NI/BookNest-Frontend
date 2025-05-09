package com.cognizant.Main.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cognizant.Main.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Products")
public class Product {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@Lob
	private String description;
	private Integer price;
//	@Column(columnDefinition="longblob")
//	private byte[] image;
	@ManyToOne(fetch=FetchType.LAZY,optional=false)
	@JoinColumn(name="category_id",nullable=false)
	@OnDelete(action=OnDeleteAction.CASCADE)
	@JsonIgnore
	private Category category;
	public ProductDTO getProductDTO()
	{
		//id,name,description,price,category.getId(),category.getName()
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(id);
		productDTO.setName(name);
		productDTO.setDescription(description);;
		productDTO.setPrice(price);
//		productDTO.setReturnedImage(image);
		productDTO.setCategoryId(category.getId());
		productDTO.setCategoryName(category.getName());
		return productDTO;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
//	public byte[] getImage() {
//		return image;
//	}
//	public void setImage(byte[] image) {
//		this.image = image;
//	}
	
}
