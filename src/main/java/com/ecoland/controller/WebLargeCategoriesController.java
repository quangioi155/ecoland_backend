package com.ecoland.controller;

import java.util.Date;
import java.util.HashMap;

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
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.system.EditOrCreateWebLargeCategoryRequest;
import com.ecoland.model.request.system.SearchWebLargeCategoryRequest;
import com.ecoland.model.response.system.SearchWebLargeCategoryResponse;
import com.ecoland.service.WebLargeCategoriesService;

/**
 * 
 * @author Tien-ITS
 * 
 */
@RestController
@RequestMapping("/api/webLargeCategories")
public class WebLargeCategoriesController {

    private static final Logger logger = LoggerFactory.getLogger(WebLargeCategoriesController.class);

    @Autowired
    private WebLargeCategoriesService webLargeCategoriesService;

    @GetMapping("/list")
    public ResponseEntity<?> getListWebCategories() {
        return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                webLargeCategoriesService.getListWebLargeCategories()));
    }

    @PostMapping("/search")
    public ResponseEntity<?> searchWebLargeCategory(@RequestBody SearchWebLargeCategoryRequest request) {
        try {
            ApiResponse response = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    webLargeCategoriesService.searchWebLargeCategory(request));

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.info(" Exception:" + e.getMessage());

            HashMap<String, String> resException = new HashMap<String, String>();
            resException.put("exceptionMes", e.getMessage());
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, resException));
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detailWebLargeCategory(@RequestParam(name = "id") int id) {
        try {
            SearchWebLargeCategoryResponse data = webLargeCategoriesService.getWebLargeCategoryDetail(id);
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, data);
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

    @PostMapping("/editOrCreate")
    public ResponseEntity<?> editOrCreateWebLargeCategory(
            @Valid @RequestBody EditOrCreateWebLargeCategoryRequest request) {
        try {
            if (request.getId() != 0) {
                return ResponseEntity.ok().body(webLargeCategoriesService.editWebLargeCategory(request));
            } else {
                return ResponseEntity.ok().body(webLargeCategoriesService.createWebLargeCategory(request));
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, e.getMessage(), null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteWebLargeCategoryById(@RequestParam(name = "id") int id) {
        webLargeCategoriesService.deleteWebLargeCategory(id);
        return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, null));
    }
}
