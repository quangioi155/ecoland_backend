package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    @Query(value = "SELECT * from sales where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<Sales> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
