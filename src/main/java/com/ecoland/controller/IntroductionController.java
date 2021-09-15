package com.ecoland.controller;

import java.util.Date;
import java.util.HashMap;

import javax.validation.Valid;

import org.hibernate.HibernateException;
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
import com.ecoland.model.request.system.FormIntroductionRequest;
import com.ecoland.model.request.system.SearchIntroductionRequest;
import com.ecoland.model.response.system.FormIntroductionResponse;
import com.ecoland.service.IntroductionService;

/**
 * @class IntroductionController
 * 
 * @controller of introduction function
 * 
 * @author ITSG - HoanNNC
 */
@RestController
@RequestMapping("/api/introduction")
public class IntroductionController {
    private static final Logger logger = LoggerFactory.getLogger(IntroductionController.class);
    
    @Autowired
    private IntroductionService introductionService;
    
    @PostMapping("/search")
    public ResponseEntity<?> searchIntroduction(@RequestBody SearchIntroductionRequest request) {
        try {
            ApiResponse response = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    introductionService.searchIntroduction(request));
            logger.info("-- search success --");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            logger.info("Exception:" + e.getMessage());

            HashMap<String, String> resException = new HashMap<String, String>();
            resException.put("exceptionMes", e.getMessage());
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, resException));
        }
    }
    
    @PostMapping("/detail")
    public ResponseEntity<?> introductionDetail(@RequestBody IdReq req) {
        try {
            FormIntroductionResponse data = introductionService.getIntroductionDetail(req.getId());
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, data);
            logger.info("-- get detail success --");
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
    public ResponseEntity<?> updateIntroduction(@Valid @RequestBody FormIntroductionRequest req, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                int result = introductionService.onChangeIntroduction(req);
                ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, result);
                logger.info("-- update introduction success --");
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
            if(e instanceof DuplicateKeyException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_ALREADY_EXISTS, null));
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteIntroduction(@RequestBody IdReq req) {
        try {
            int data = introductionService.softDeleteIntroduction(req.getId());
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.DELETE_SUCCESS, data);
            logger.info("-- delete introduction success --");
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            logger.info(" Exception:" + e.getMessage());
            if (e instanceof RecordNotFoundException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_DOES_NOT_EXIST, null));
            }
            if(e instanceof HibernateException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_REFER, null));
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }
}
