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
import com.ecoland.model.request.system.EditOrCreateBranchRequest;
import com.ecoland.model.request.system.SearchBranchRequest;
import com.ecoland.service.BranchesService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/branches")
public class BranchesController {

    private static final Logger logger = LoggerFactory.getLogger(BranchesController.class);

    @Autowired
    private BranchesService branchesService;

    /**
     * 
     * @author thaotv@its-global.vn
     * @return list branches
     * 
     */
    @GetMapping("/list")
    public ResponseEntity<?> getListBranches() {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, branchesService.getListBranches()));
        } catch (Exception e) {
            logger.error("-- Error list branches --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }

    @PostMapping("/search")
    public ResponseEntity<?> searchBranches(@RequestBody SearchBranchRequest request) {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, branchesService.searchBranch(request)));
        } catch (Exception e) {
            logger.error("-- Error search branches --" + e);
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_404, e.getMessage(), null));
        }
    }

    @PostMapping("/deleteBranch")
    public ResponseEntity<?> deleteUserGroupById(@RequestParam(name = "id") Integer id) {
        try {
            branchesService.deleteBranchById(id);
        } catch (Exception e) {
            logger.error("-- Error delete branches by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
        return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null));
    }

    @GetMapping("/detailById")
    public ResponseEntity<?> detailBranchById(@RequestParam(name = "id") Integer id) {
        try {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Constants.HTTP_CODE_200, null, branchesService.detailBranchById(id)));
        } catch (Exception e) {
            logger.error("-- Error detail branches by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
    }

    @PostMapping("/createOrEditBranch")
    public ResponseEntity<?> createOrEditUserGroup(@RequestBody EditOrCreateBranchRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(branchesService.editBranch(request));
            } else {
                return ResponseEntity.ok().body(branchesService.createBranch(request));
            }
        } catch (Exception e) {
            logger.error("-- Error create or edit branches --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }
}
