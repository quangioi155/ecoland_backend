package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.UserAccounts;
import com.ecoland.entity.UserGroups;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.EditOrCreateUserGroupRequest;
import com.ecoland.model.request.SearchUserGroupsRequest;
import com.ecoland.model.response.DetailUserGroupsResponse;
import com.ecoland.model.response.DropdownListResponse;
import com.ecoland.repository.CommonDAO;
import com.ecoland.repository.UserAccountsRepository;
import com.ecoland.repository.UserGroupsRepository;
import com.ecoland.repository.dao.UserGroupsDAO;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
@Transactional
public class UserGroupsService {
    private static final Logger logger = LoggerFactory.getLogger(UserGroupsService.class);

    @Autowired
    private UserGroupsRepository userGroupsRepository;

    @Autowired
    private UserAccountsRepository userAccountsRepository;

    @Autowired
    private UserGroupsDAO userGroupsDAO;

    @Autowired
    private CommonService commonService;

    @Autowired
    private CommonDAO commonDAO;

    /**
     * 
     * @author thaotv@its-global.vn
     * @return list user group (idDropdown and nameDropdown)
     * 
     */
    @CacheEvict(value = "userGroupsDropdownlist", allEntries = true)
    public List<DropdownListResponse> userGroupsDropdownList() {
        logger.info("-- Get infomation dropdownlist user groups --");
        return userGroupsRepository.userGroupsDropdownList();
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @return optional user group
     * 
     */
    public Optional<UserGroups> findByIdAndDeleteFlagUserGroups(Integer id, Integer deleteFlag) {
        return userGroupsRepository.findByIdAndDeleteFlag(id, deleteFlag);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return result page response
     * 
     */
    @CacheEvict(value = "userGroupsSearch", allEntries = true)
    public ResultPageResponse searchUserGroups(SearchUserGroupsRequest request) {
        logger.info("-- Get information user group --");
        return userGroupsDAO.searchUserGroup(request);
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return update delete flag = 1
     * 
     */
    public void deleteUserGroupById(Integer id) throws Exception {
        logger.info("-- Delete user group by id -- ");
        Optional<UserGroups> optionalUserGroup = userGroupsRepository.findByIdAndDeleteFlag(id, 0);
        if (optionalUserGroup.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            throw new Exception(Constants.RECORD_NOT_FOUND);
        }
        List<UserAccounts> userAccounts = userAccountsRepository.findByUserGroupId(id);
        if (userAccounts.size() > 0) {
            logger.error(Constants.USER_GROUP_ASSIGN_USER_ACCOUNTS);
            throw new Exception(Constants.USER_GROUP_ASSIGN_USER_ACCOUNTS);
        }
        commonDAO.sqlCommonDAO(Constants.USER_GROUPS, Long.valueOf(id), Constants.DELETE,
                commonService.idUserAccountLogin());
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param id
     * @return detail user group response
     * 
     */
    public DetailUserGroupsResponse detailUserGroupById(Integer id) {
        logger.info("-- Get infomation detail user group --");
        Optional<UserGroups> optionalUserGroups = findByIdAndDeleteFlagUserGroups(id, 0);
        DetailUserGroupsResponse detail = new DetailUserGroupsResponse();
        if (!optionalUserGroups.isEmpty()) {
            detail.setId(optionalUserGroups.get().getId());
            detail.setGroupName(optionalUserGroups.get().getGroupName());
            detail.setContactCustomerFlag(optionalUserGroups.get().getContactCustomerFlag());
            detail.setDriverFlag(optionalUserGroups.get().getDriverFlag());
            detail.setVehicleDispatchFlag(optionalUserGroups.get().getVehicleDispatchFlag());
            detail.setZecFlag(optionalUserGroups.get().getZecFlag());
            detail.setManageFlag(optionalUserGroups.get().getManageFlag());
            detail.setWarehouseFlag(optionalUserGroups.get().getWarehouseFlag());
            detail.setSystemFlag(optionalUserGroups.get().getSystemFlag());
        }
        return detail;
    }

    /**
     * create a user group
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return obj ApiResponse
     * 
     */
    public ApiResponse createUserGroup(EditOrCreateUserGroupRequest request) {
        logger.info("-- Create a user group --");
        Optional<UserGroups> userGroups = userGroupsRepository.findByGroupName(request.getGroupName().trim());
        Integer idLogin = commonService.idUserAccountLogin();
        if (!userGroups.isEmpty()) {
            logger.error(Constants.RECORD_ALREADY_EXISTS);
            return new ApiResponse(Constants.HTTP_CODE_403, null, userGroups);
        } else {
            UserGroups userGroup = new UserGroups();
            userGroup.setGroupName(request.getGroupName().trim());
            userGroup.setContactCustomerFlag(request.getContactCustomerFlag());
            userGroup.setDriverFlag(request.getDriverFlag());
            userGroup.setVehicleDispatchFlag(request.getVehicleDispatchFlag());
            userGroup.setZecFlag(request.getZecFlag());
            userGroup.setManageFlag(request.getManageFlag());
            userGroup.setWarehouseFlag(request.getWarehouseFlag());
            userGroup.setSystemFlag(request.getSystemFlag());
            userGroup.setCreateAt(new Timestamp(new Date().getTime()));
            userGroup.setUpdatedAt(new Timestamp(new Date().getTime()));
            userGroup.setCreatedBy(idLogin);
            userGroup.setUpdatedBy(idLogin);
            userGroup.setDeleteFlag(0);
            userGroupsRepository.save(userGroup);
            return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
        }
    }

    /**
     * Edit a user group
     * 
     * @author thaotv@its-global.vn
     * @param request
     * @return obj ApiResponse
     * 
     */
    public ApiResponse editUserGroup(EditOrCreateUserGroupRequest request) {
        logger.info("-- Edit a user group --");
        Optional<UserGroups> optinalUserGroup = userGroupsRepository.findById(request.getId());
        if (optinalUserGroup.isEmpty()) {
            logger.error(Constants.RECORD_NOT_FOUND);
            return new ApiResponse(Constants.HTTP_CODE_404, Constants.RECORD_NOT_FOUND, request);
        } else {
            Optional<UserGroups> userGroups = userGroupsRepository.findByGroupName(request.getGroupName().trim());
            if (!userGroups.isEmpty() && userGroups.get().getId().compareTo(request.getId()) != 0) {
                logger.error(Constants.RECORD_ALREADY_EXISTS);
                return new ApiResponse(Constants.HTTP_CODE_403, Constants.RECORD_ALREADY_EXISTS, request);
            }
            optinalUserGroup.get().setGroupName(request.getGroupName().trim());
            optinalUserGroup.get().setContactCustomerFlag(request.getContactCustomerFlag());
            optinalUserGroup.get().setDriverFlag(request.getDriverFlag());
            optinalUserGroup.get().setVehicleDispatchFlag(request.getVehicleDispatchFlag());
            optinalUserGroup.get().setZecFlag(request.getZecFlag());
            optinalUserGroup.get().setManageFlag(request.getManageFlag());
            optinalUserGroup.get().setWarehouseFlag(request.getWarehouseFlag());
            optinalUserGroup.get().setSystemFlag(request.getSystemFlag());
            userGroupsRepository.save(optinalUserGroup.get());
            commonDAO.sqlCommonDAO(Constants.USER_GROUPS, Long.valueOf(request.getId()), Constants.UPDATE,
                    commonService.idUserAccountLogin());
        }
        return new ApiResponse(Constants.HTTP_CODE_200, Constants.CREATE_SUCCESS, null);
    }
}
