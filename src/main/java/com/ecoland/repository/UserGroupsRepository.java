package com.ecoland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecoland.entity.UserGroups;
import com.ecoland.model.response.DropdownListResponse;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public interface UserGroupsRepository extends JpaRepository<UserGroups, Integer> {

    Optional<UserGroups> findById(Integer id);

    Optional<UserGroups> findByIdAndDeleteFlag(Integer id, Integer deleteFlag);

    @Query(value = "SELECT ug.id as value, ug.group_name as name FROM user_groups ug where ug.delete_flag = 0", nativeQuery = true)
    List<DropdownListResponse> userGroupsDropdownList();

    Optional<UserGroups> findByGroupName(String groupName);
}
