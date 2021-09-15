package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.GetStockCodes;

@Repository
public interface GetStockCodesRepository extends JpaRepository<GetStockCodes, Long> {

    @Query(value = "SELECT * from get_stock_codes where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<GetStockCodes> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
