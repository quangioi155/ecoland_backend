package com.ecoland.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.EditOrCreateUserGroupRequest;
import com.ecoland.model.request.SearchUserGroupsRequest;
import com.ecoland.service.UserGroupsService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/user_groups")
public class UserGroupsController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupsController.class);

    @Autowired
    private UserGroupsService userGroupsService;

    @PostMapping("/search")
    public ResponseEntity<?> searchUserGroups(@RequestBody SearchUserGroupsRequest request) {
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    userGroupsService.searchUserGroups(request)));
        } catch (Exception e) {
            logger.error("-- Error search -- " + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
    }

    @PostMapping("/deleteUserGroups")
    public ResponseEntity<?> deleteUserGroupById(@RequestParam(name = "id") Integer id) {
        try {
            userGroupsService.deleteUserGroupById(id);
        } catch (Exception e) {
            logger.error("-- Error delete user groups -- " + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
        return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null));
    }

    @GetMapping("/detailById")
    public ResponseEntity<?> detailUserGroupById(@RequestParam(name = "id") Integer id) {
        try {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Constants.HTTP_CODE_200, null, userGroupsService.detailUserGroupById(id)));
        } catch (Exception e) {
            logger.error("-- Error detail by id user groups -- " + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }

    @PostMapping("/createOrEditUserGroup")
    public ResponseEntity<?> createOrEditUserGroup(@RequestBody EditOrCreateUserGroupRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(userGroupsService.editUserGroup(request));
            } else {
                return ResponseEntity.ok().body(userGroupsService.createUserGroup(request));
            }
        } catch (Exception e) {
            logger.error("-- Error create or edit user groups -- " + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }
}
