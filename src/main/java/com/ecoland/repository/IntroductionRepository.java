package com.ecoland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.Introductions;

/**
 * @interface IntroductionRepository
 * 
 * @summary repo of introduction function
 * 
 * @author ITSG - HoanNNC
 */
@Repository
public interface IntroductionRepository extends JpaRepository<Introductions, Integer> {

    Optional<Introductions> findByIdAndDeleteFlag(Integer id, Integer delFlag);

    int countByIntroductionNameAndDeleteFlag(String introductionName, Integer deleteNone);
}
