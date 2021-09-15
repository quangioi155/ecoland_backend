package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.ProductRanks;
import com.ecoland.model.response.DropdownListResponse;

/**
 * @interface ProductRanksRepository
 * 
 * @summary repo of product rank function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public interface ProductRanksRepository extends JpaRepository<ProductRanks, Integer> {

    Optional<ProductRanks> findByIdAndDeleteFlag(Integer id, Integer delFlag);

    @Query(value = "SELECT pr.id as value, pr.product_rank_name as name FROM product_ranks pr where pr.delete_flag = 0", nativeQuery = true)
    List<DropdownListResponse> productRankDropDownList();

    int countByProductRankNameAndDeleteFlag(String productRankName, Integer delFlag);
}
