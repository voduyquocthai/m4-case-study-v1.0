package com.blueteam.official.repository;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.Product;
import com.blueteam.official.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM case_study_m4.category WHERE name like ?1", nativeQuery = true)
    Page<Category> findAllCategoryByNameUsingQuery(String name, Pageable pageable);
}
