package com.cognizant.Main.entities;
import com.cognizant.Main.dto.CategoryDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
     private Long id;
     private String name;
     @Lob
     private String description;
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
	
	public CategoryDTO getCategoryDTO()
	{
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(id);
		categoryDTO.setName(name);
		categoryDTO.setDescription(description);
		return categoryDTO;
	}
     
}
