package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Estimates;

@Repository
public interface EstimatesRepository extends JpaRepository<Estimates, Long> {

    @Query(value = "SELECT * from estimates where delete_flag =:deleteFlag and sales_branch_id =:id", nativeQuery = true)
    List<Estimates> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
