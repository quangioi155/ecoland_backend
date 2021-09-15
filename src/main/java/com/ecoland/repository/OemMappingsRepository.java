package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.OemMappings;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface OemMappingsRepository extends JpaRepository<OemMappings, Long> {
    @Query(value = "select * from oem_mappings where product_category_id=:productCategoryId and delete_flag=:deleteFlag", nativeQuery = true)
    List<OemMappings> findByProductCategoryIdAndDeleteFlag(Integer productCategoryId, Integer deleteFlag);

}
