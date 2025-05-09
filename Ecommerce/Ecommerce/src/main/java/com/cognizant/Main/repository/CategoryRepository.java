package com.cognizant.Main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.Main.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

}
