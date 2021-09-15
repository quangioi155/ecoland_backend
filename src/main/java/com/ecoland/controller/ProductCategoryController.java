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
import com.ecoland.model.request.RequestDTO;
import com.ecoland.model.request.system.EditOrCreateProductCategoryRequest;
import com.ecoland.service.ProductCategoryService;

@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping("/search")
    public ResponseEntity<?> searchProductCategory(@RequestBody RequestDTO request) {
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    productCategoryService.searchProductCategory(request)));
        } catch (Exception e) {
            logger.error("-- Error search product category --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }

    @GetMapping("/detailById")
    public ResponseEntity<?> detailProductCategory(@RequestParam(name = "id") Integer id) {
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    productCategoryService.detailProductCategory(id)));
        } catch (Exception e) {
            logger.error("-- Error detail product category by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }

    @PostMapping("/deleteProductCategory")
    public ResponseEntity<?> deleteUserGroupById(@RequestParam(name = "id") Integer id) {
        try {
            productCategoryService.deleteProductCategoryById(id);
        } catch (Exception e) {
            logger.error("-- Error delete product category by id --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
        return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null));
    }

    @PostMapping("/createOrEditProductCategory")
    public ResponseEntity<?> createOrEditUserGroup(@RequestBody EditOrCreateProductCategoryRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(productCategoryService.editProductCategory(request));
            } else {
                return ResponseEntity.ok().body(productCategoryService.createProductCategory(request));
            }
        } catch (Exception e) {
            logger.error("-- Error create or edit product category --" + e);
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }

    }
}
