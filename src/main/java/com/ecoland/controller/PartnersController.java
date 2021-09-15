package com.ecoland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.model.ApiResponse;
import com.ecoland.service.PartnerService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/partners")
public class PartnersController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("/list")
    public ResponseEntity<?> getListPartners() {
	return ResponseEntity.ok().body(
		new ApiResponse(Constants.HTTP_CODE_200, Constants.SUCCESS, partnerService.getListPartners()));
    }
}
