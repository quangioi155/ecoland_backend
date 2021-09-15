package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.MainSystemouts;

@Repository
public interface MainSystemoutsRepository extends JpaRepository<MainSystemouts, Long> {

    @Query(value = "SELECT * from main_systemouts where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<MainSystemouts> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
