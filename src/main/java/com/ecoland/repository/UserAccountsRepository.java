package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.UserAccounts;

/**
 * 
 * @author thaotv@its-global.vn
 * @update ITSG-HoanNNC
 */
@Repository
public interface UserAccountsRepository extends JpaRepository<UserAccounts, Long> {
    Optional<UserAccounts> findByLoginIdAndDeleteFlag(String loginId, Integer deleteFlag);

    Optional<UserAccounts> findByIdAndDeleteFlag(Long id, Integer deleteFlag);

    Page<UserAccounts> findAll(Pageable pageable);

    Optional<UserAccounts> findById(Integer Id);

    Optional<UserAccounts> findByLoginId(String loginId);

    @Query(value = "SELECT * from user_accounts WHERE delete_flag = 0 AND user_group_id in(:id)", nativeQuery = true)
    List<UserAccounts> findByUserGroupId(Integer id);
    
    /** HoanNNC - dao check delete partner */
    @Query("SELECT COUNT(*) FROM UserAccounts uc WHERE uc.partners.id = :partnerId")
    Integer countAccountByPartnerId(@Param("partnerId") Integer partnerId);

    @Query(value = "SELECT * from user_accounts where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<UserAccounts> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
