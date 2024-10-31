package vn.iotstar.thymeleaf.repository;

import java.util.List;
import java.util.Locale.Category;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iotstar.thymeleaf.entity.CategoryEntity;



public interface CategoryRepository extends JpaRepository<Category, Long> {

	List<CategoryEntity>  findByNameContaining(String name);
	Page<CategoryEntity> findByNameContaining(String name, Pageable pagaable);
}
