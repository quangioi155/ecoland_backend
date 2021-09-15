package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Partners;
import com.ecoland.model.response.DropdownListResponse;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface PartnersRepository extends JpaRepository<Partners, Long> {
    Optional<Partners> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);

    Optional<Partners> findByPartnerName(String partnerName);

    @Query(value = "SELECT pn.id as value, pn.partner_name as name FROM partners pn where pn.delete_flag = 0", nativeQuery = true)
    List<DropdownListResponse> partnersDropdownList();

    Long countByPartnerNameAndDeleteFlag(String partnerName, Integer delFlag);
}
