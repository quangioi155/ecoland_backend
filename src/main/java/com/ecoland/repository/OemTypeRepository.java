package com.ecoland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.OemTypes;

/**
 * @interface OemTypeRepository
 * 
 * @summary repo of oem type function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public interface OemTypeRepository extends JpaRepository<OemTypes, Integer> {

    Optional<OemTypes> findByIdAndDeleteFlag(Integer id, Integer delFlag);
    
    int countByOemNameAndDeleteFlag(String oemName, Integer delFlag);
}
