package com.ecoland.controller;

import java.util.Date;

import com.ecoland.service.WebSmallCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.service.BranchesService;
import com.ecoland.service.PartnerService;
import com.ecoland.service.ProductRankService;
import com.ecoland.service.UserGroupsService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/dropdownlist")
public class DropdownListController {

    private static final Logger logger = LoggerFactory.getLogger(DropdownListController.class);

    @Autowired
    private UserGroupsService userGroupsService;

    @Autowired
    private BranchesService branchesService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ProductRankService productRankService;

    @Autowired
    private WebSmallCategoryService smallCategoryService;

    @GetMapping("/user-group")
    public ResponseEntity<?> userGroupDropDownList() {
        logger.warn("-- User group dropdown list --");
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    userGroupsService.userGroupsDropdownList()));
        } catch (Exception e) {
            logger.error("-- Error get dropdown list --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @GetMapping("/branches")
    public ResponseEntity<?> branchesDropDownList() {
        logger.warn("-- Branches dropdown list --");
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    branchesService.branchesDropdownList()));
        } catch (Exception e) {
            logger.error("-- Error get dropdown list --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @GetMapping("/branchesById")
    public ResponseEntity<?> branchesByPartnerId(@RequestParam(name = "id") Integer id) {
        logger.warn("-- Branches by partner id dropdown list --");
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    branchesService.brancheByPartnerId(id)));
        } catch (Exception e) {
            logger.error("-- Error get dropdown list --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @GetMapping("/partners")
    public ResponseEntity<?> partnersDropDownList() {
        logger.warn("-- Partners dropdown list --");
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, partnerService.partnersDropdownList()));
        } catch (Exception e) {
            logger.error("-- Error get dropdown list --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @GetMapping("/productRank")
    public ResponseEntity<?> productRankDropDownList() {
        logger.warn("-- Product rank dropdown list --");
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    productRankService.productRankDropDownList()));
        } catch (Exception e) {
            logger.error(" -- Error get dropdown list product rank --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @GetMapping("/listLargeCategory")
    public ResponseEntity<?> getLargeCategory() {
        logger.warn("-- Large category dropdown list --");
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    smallCategoryService.getLargeCategory()));
        } catch (Exception e) {
            logger.error("-- Error get dropdown list --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }
}
