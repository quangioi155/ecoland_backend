package com.ecoland.controller;

import com.ecoland.common.Constants;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.system.EditOrCreateOutsourceCompanyRequest;
import com.ecoland.model.request.system.SearchOutsourceCompanyRequest;
import com.ecoland.service.OutsourceCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/outsource-companies")
public class OutSourceCompanyController {

    private static final Logger logger = LoggerFactory.getLogger(OutSourceCompanyController.class);

    @Autowired
    private OutsourceCompanyService outsourceCompanyService;

    @PostMapping("/search")
    public ResponseEntity<?> searchBranches(@RequestBody SearchOutsourceCompanyRequest request) {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, outsourceCompanyService.searchOutSourceCompany(request)));
        } catch (Exception e) {
            logger.error("-- Error search outsource company --" + e);
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_404, e.getMessage(), null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCompany(@RequestBody SearchOutsourceCompanyRequest request) {
        try {
            outsourceCompanyService.deleteOutsourceCompany(request.getId());
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, request.getId());
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            logger.info(" Exception:" + e.getMessage());
            if (e instanceof RecordNotFoundException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_DOES_NOT_EXIST, null));
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/createOrEditProductCategory")
    public ResponseEntity<?> createOrEditUserGroup(@RequestBody EditOrCreateOutsourceCompanyRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(outsourceCompanyService.editProductCategory(request));
            } else {
                return ResponseEntity.ok().body(outsourceCompanyService.createProductCategory(request));
            }
        } catch (Exception e) {
            logger.error("-- Error create or edit product category --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
    }
}
