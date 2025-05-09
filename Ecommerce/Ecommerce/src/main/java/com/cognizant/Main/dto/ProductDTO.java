//package com.cognizant.Main.dto;
//
////import org.springframework.web.multipart.MultipartFile;
//
//public class ProductDTO {
//
//	public ProductDTO(Long id, String name, String description, Integer price, Long categoryId, String categoryName) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.description = description;
//		this.price = price;
//		this.categoryId = categoryId;
//		this.categoryName = categoryName;
//	}
//
//	private Long id;
//	private String name;
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public String getDescription() {
//		return description;
//	}
//	public void setDescription(String description) {
//		this.description = description;
//	}
//	public Integer getPrice() {
//		return price;
//	}
//	public void setPrice(Integer price) {
//		this.price = price;
//	}
// 
////	public MultipartFile getImage() {
////		return image;
////	}
////	public void setImage(MultipartFile image) {
////		this.image = image;
////	}
//
//	private String description;
//	private Integer price;
////	private MultipartFile image;
////	private byte[] returnedImage;
//	private Long categoryId;
//	private String categoryName;
////	public byte[] getReturnedImage() {
////		return returnedImage;
////	}
////	public void setReturnedImage(byte[] returnedImage) {
////		this.returnedImage = returnedImage;
////	}
//	public Long getCategoryId() {
//		return categoryId;
//	}
//	public void setCategoryId(Long categoryId) {
//		this.categoryId = categoryId;
//	}
//	public String getCategoryName() {
//		return categoryName;
//	}
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}
//	
//}



package com.cognizant.Main.dto;

public class ProductDTO {
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Integer price, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    private Long id;
    private String name;
    private String description;
    private Integer price;
    private Long categoryId;
    private String categoryName;

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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

