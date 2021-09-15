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
import com.ecoland.model.request.system.FormCompanyRequest;
import com.ecoland.model.request.system.SearchCompanyRequest;
import com.ecoland.model.response.system.FormCompanyResponse;
import com.ecoland.service.PartnerService;

/**
 * @class CompanyGroupController
 * 
 * @controller of company group function
 * 
 * @author ITSG - HoanNNC
 */
@RestController
@RequestMapping("/api/company")
public class CompanyGroupController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyGroupController.class);

    @Autowired
    private PartnerService partnerService;

    @PostMapping("/search")
    public ResponseEntity<?> searchCompanyGroup(@RequestBody SearchCompanyRequest request) {
        try {
            ApiResponse response = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS,
                    partnerService.searchCompanyGroup(request));

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
    public ResponseEntity<?> detailCompany(@RequestBody IdReq req) {
        try {
            FormCompanyResponse data = partnerService.getPartnerDetail(req.getId());
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
    public ResponseEntity<?> updateCompany(@Valid @RequestBody FormCompanyRequest req, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasErrors()) {
                int result = partnerService.onChangePartner(req);
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
    public ResponseEntity<?> deleteCompany(@RequestBody SearchCompanyRequest req) {
        try {
            partnerService.deletePartner(req.getId());
            ApiResponse res = new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, req.getId());
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
