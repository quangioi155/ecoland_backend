package com.ecoland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.ProductCategory;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    Optional<ProductCategory> findById(Integer id);
}
