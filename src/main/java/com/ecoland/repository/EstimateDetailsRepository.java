package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.EstimateDetails;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface EstimateDetailsRepository extends JpaRepository<EstimateDetails, Long> {

    @Query(value = "select * from estimate_details where product_category_id =:productCategoryId and delete_flag =:deleteFlag", nativeQuery = true)
    List<EstimateDetails> findByProductCategoryIdAndDeleteFlag(Integer productCategoryId, Integer deleteFlag);
}
