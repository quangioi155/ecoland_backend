package com.ecoland.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.service.AutoCompleteService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/autocomplete")
public class AutoCompleteController {

    private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);

    @Autowired
    private AutoCompleteService autoCompleteService;

    @GetMapping("/listWebSmall")
    public ResponseEntity<?> autoCompletes() {
        try {
            return ResponseEntity.ok().body(
                    new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, autoCompleteService.autoCompletes()));
        } catch (Exception e) {
            logger.error("-- Error get web small --" + e);
            return ResponseEntity.ok().body(new ErrorResponse(Constants.HTTP_CODE_400, new Date(), e.getMessage(), null));
        }
    }
}
