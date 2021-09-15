package com.ecoland.controller;

import java.util.Date;

import javax.validation.Valid;

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
import com.ecoland.model.request.EditOrCreateUserRequest;
import com.ecoland.model.request.UserAccountListRequest;
import com.ecoland.service.UserAccountsService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/user")
public class UserAccountsController {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountsController.class);

    @Autowired
    private UserAccountsService userService;

    @PostMapping("/userAccountList")
    public ResponseEntity<?> getAllcommentsByPostId(@RequestBody UserAccountListRequest request) {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, userService.userAccountList(request)));
        } catch (Exception e) {
            logger.error("-- Error user account --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/deleteUserAccountById")
    public ResponseEntity<?> deleteUserAccountById(@RequestParam(name = "id") Long id) {
        try {
            userService.deleteUserAccount(id);
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null));
        } catch (Exception e) {
            logger.error("-- Error delete user account by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), Constants.ERROR, null));
        }

    }

    @PostMapping("/editOrCreateUserAccount")
    public ResponseEntity<?> editOrCreateUserAccount(@Valid @RequestBody EditOrCreateUserRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(userService.editUserAccounts(request));
            } else {
                return ResponseEntity.ok().body(userService.createUserAccounts(request));
            }
        } catch (Exception e) {
            logger.error("-- Error create or edit user account --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }

    @GetMapping("/detail")
    public ResponseEntity<?> detailUserAccounts(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    userService.detailUserAccountsById(id)));
        } catch (Exception e) {
            logger.error("-- Error detail user account by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
    }
}
