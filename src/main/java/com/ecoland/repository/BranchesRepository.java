package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Branches;
import com.ecoland.model.response.DropdownListResponse;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface BranchesRepository extends JpaRepository<Branches, Long> {
    Optional<Branches> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);

    @Query(value = "SELECT b.id as value, b.branch_short_name as name FROM branches b where b.delete_flag = 0", nativeQuery = true)
    List<DropdownListResponse> branchesDropdownList();
    
    @Query(value = "SELECT b.id as value, b.branch_short_name as name FROM branches b where b.delete_flag = 0 AND b.partner_id =:id", nativeQuery = true)
    List<DropdownListResponse> branchesByPartnerId(Integer id);
}
