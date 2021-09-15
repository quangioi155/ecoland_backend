package com.ecoland.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.model.ApiResponse;
import com.ecoland.service.HeaderService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/header")
public class HeaderController {

    @Autowired
    private HeaderService headerService;

    @GetMapping("/inforUserLogin")
    public ResponseEntity<?> getInformationUserLogin() {
	return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_200, null, headerService.infoUserLogin()));
    }
}
