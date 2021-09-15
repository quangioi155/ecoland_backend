package com.ecoland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.MainSystemProjects;

@Repository
public interface MainSystemProjectsRepository extends JpaRepository<MainSystemProjects, Long>{

    @Query(value = "SELECT * from main_system_projects where delete_flag =:deleteFlag and branch_id =:id", nativeQuery = true)
    List<MainSystemProjects> findByBranchIdAndDeleteFlag(Integer id, Integer deleteFlag);
}
