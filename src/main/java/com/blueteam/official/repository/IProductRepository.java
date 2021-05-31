package com.blueteam.official.repository;

import com.blueteam.official.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameContaining(String name, Pageable pageable);

    @Query(value = "SELECT * FROM case_study_m4.product WHERE name like ?1", nativeQuery = true)
    Page<Product> findAllProductByNameUsingQuery(String name, Pageable pageable);

}
