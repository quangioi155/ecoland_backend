package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Stocks;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {

    @Query(value = "select * from stocks where product_category_id =:productCategoryId and delete_flag =:deleteFlag", nativeQuery = true)
    List<Stocks> findByProductCategoryIdAndDeleteFlag(Integer productCategoryId, Integer deleteFlag);
}
