package com.ecoland.controller;

import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.IdReq;
import com.ecoland.model.request.system.FormRankRequest;
import com.ecoland.model.request.system.SearchRankRequest;
import com.ecoland.model.response.system.FormRankResponse;
import com.ecoland.service.ProductRankService;

/**
 * @class ProductRankController
 * 
 * @controller of product rank function
 * 
 * @author ITSG - HoanNNC
 */
@RestController
@RequestMapping("/api/rank")
public class ProductRankController {
    private static final Logger logger = LoggerFactory.getLogger(ProductRankController.class);

    @Autowired
    private ProductRankService productRankService;

    @PostMapping("/search")
    public ResponseEntity<?> searchRank(@RequestBody SearchRankRequest request) {
        try {
            ApiResponse response = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    productRankService.searchRank(request));

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.info(" Exception:" + e.getMessage());

            HashMap<String, String> resException = new HashMap<String, String>();
            resException.put("exceptionMes", e.getMessage());
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, resException));
        }
    }

    @PostMapping("/detail")
    public ResponseEntity<?> rankDetail(@RequestBody IdReq req) {
        try {
            FormRankResponse data = productRankService.getRankDetail(req.getId());
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

    @PostMapping("/update")
    public ResponseEntity<?> updateRank(@Valid @RequestBody FormRankRequest req, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                int result = productRankService.onChangeRank(req);
                ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, result);
                return ResponseEntity.ok().body(res);
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_401, new Date(), Constants.FIELD_INVALID, null));
        } catch (Exception e) {
            logger.info(" Exception:" + e.getMessage());
            if (e instanceof RecordNotFoundException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_401, new Date(), Constants.RECORD_DOES_NOT_EXIST, null));
            }
            if (e instanceof DuplicateKeyException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_ALREADY_EXISTS, null));
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteRank(@RequestBody IdReq req) {
        try {
            int data = productRankService.softDeleteRank(req.getId());
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
}
