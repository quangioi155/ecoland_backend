package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.BranchesLocations;

@Repository
public interface BranchesLocationsRepository extends JpaRepository<BranchesLocations, Long> {

    @Query(value = "SELECT * from branches_locations where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<BranchesLocations> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);

}
