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
import com.ecoland.model.request.system.FormOemTypeRequest;
import com.ecoland.model.request.system.SearchOemTypeRequest;
import com.ecoland.model.response.system.FormOemTypeResponse;
import com.ecoland.service.OemTypeService;

/**
 * @class OemTypeController
 * 
 * @controller of oem type function
 * 
 * @author ITSG - HoanNNC
 */
@RestController
@RequestMapping("/api/oemType")
public class OemTypeController {
    private static final Logger logger = LoggerFactory.getLogger(ProductRankController.class);

    @Autowired
    private OemTypeService oemTypeService;

    @PostMapping("/search")
    public ResponseEntity<?> searchOemType(@RequestBody SearchOemTypeRequest request) {
        try {
            ApiResponse response = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    oemTypeService.searchOemType(request));

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
    public ResponseEntity<?> oemTypeDetail(@RequestBody IdReq req) {
        try {
            FormOemTypeResponse data = oemTypeService.getOemTypeDetail(req.getId());
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
    public ResponseEntity<?> updateOemType(@Valid @RequestBody FormOemTypeRequest req, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                int result = oemTypeService.onChangeOemType(req);
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
            if(e instanceof DuplicateKeyException) {
                return ResponseEntity.ok().body(
                        new ErrorResponse(Constants.HTTP_CODE_403, new Date(), Constants.RECORD_ALREADY_EXISTS, null));
            }
            return ResponseEntity.ok()
                    .body(new ErrorResponse(Constants.HTTP_CODE_404, new Date(), Constants.ERROR, null));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteOemType(@RequestBody IdReq req) {
        try {
            int data = oemTypeService.softDeleteOemType(req.getId());
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, data);
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            logger.info("Exception:" + e.getMessage());
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
