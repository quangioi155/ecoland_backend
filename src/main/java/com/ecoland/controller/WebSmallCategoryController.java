package com.ecoland.controller;

import com.ecoland.common.Constants;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.EditOrCreateWebSmallCateRequest;
import com.ecoland.model.request.WebSmallCategoryRequest;
import com.ecoland.service.WebSmallCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/smallCategory")
public class WebSmallCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(WebSmallCategoryController.class);

    @Autowired
    private WebSmallCategoryService smallCategoryService;

    @PostMapping("/list")
    public ResponseEntity<?> getSmallCategory(@RequestBody WebSmallCategoryRequest request) {
        try {
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    smallCategoryService.getSmallCategory(request)));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            smallCategoryService.delete(id);
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, id);
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

    @GetMapping("/detail")
    public ResponseEntity<?> detail(@RequestParam("id") Integer id) {
        try {
            return ResponseEntity.ok()
                    .body(new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, smallCategoryService.detail(id)));
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

    @PostMapping("/editOrCreateSmallCategory")
    public ResponseEntity<?> editOrCreate(@Valid @RequestBody EditOrCreateWebSmallCateRequest request) {
        try {
            if (request.getId() != null) {
                return ResponseEntity.ok().body(smallCategoryService.edit(request));
            } else {
                return ResponseEntity.ok().body(smallCategoryService.create(request));
            }
        } catch (Exception e) {
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), e.getMessage(), null));
        }
    }

}
