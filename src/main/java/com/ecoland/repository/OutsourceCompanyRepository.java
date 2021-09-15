package com.ecoland.repository;

import com.ecoland.entity.OutsourceCompanies;
import com.ecoland.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@Repository
public interface OutsourceCompanyRepository extends JpaRepository<OutsourceCompanies, Long> {

    Optional<OutsourceCompanies> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);
    Optional<OutsourceCompanies> findById(Integer id);
}
