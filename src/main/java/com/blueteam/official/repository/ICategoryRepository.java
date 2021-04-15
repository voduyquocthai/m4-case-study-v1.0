package com.blueteam.official.repository;

import com.blueteam.official.model.Category;
import com.blueteam.official.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
