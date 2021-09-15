package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.WebLargeCategories;
import com.ecoland.model.response.DropdownListResponse;

/**
 * 
 * @author Tien-ITS
 * 
 */
@Repository
public interface WebLargeCategoriesRepository extends JpaRepository<WebLargeCategories, Long> {
    Optional<WebLargeCategories> findByCategoryName(String categoryName);

    Optional<WebLargeCategories> findById(Integer id);

    Optional<WebLargeCategories> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);

    @Query(value = "SELECT w.id as value, w.category_name as name FROM web_large_categories w where w.delete_flag = 0", nativeQuery = true)
    List<DropdownListResponse> webLargeCategoriesDropdownList();
}
