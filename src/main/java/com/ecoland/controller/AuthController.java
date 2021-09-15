package com.ecoland.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoland.common.Constants;
import com.ecoland.event.OnUserLogoutSuccessEvent;
import com.ecoland.model.ApiResponse;
import com.ecoland.model.ErrorResponse;
import com.ecoland.model.request.LogOutRequest;
import com.ecoland.model.request.LoginRequest;
import com.ecoland.model.response.LoginResponse;
import com.ecoland.security.UserDetailsImpl;
import com.ecoland.service.CurrentUser;
import com.ecoland.service.UserAccountsService;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserAccountsService userService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
	LoginResponse loginResponse = userService.login(request);
	if (loginResponse == null) {
	    return ResponseEntity.ok()
		    .body(new ErrorResponse(Constants.HTTP_CODE_401, new Date(), Constants.LOGG_IN_ERROR, null));
	}
	return ResponseEntity.ok().body(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@CurrentUser UserDetailsImpl currentUser,
	    @RequestBody LogOutRequest logOutRequest) {
	try {
	    OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(currentUser.getUsername(),
		    logOutRequest.getToken(), logOutRequest);
	    applicationEventPublisher.publishEvent(logoutSuccessEvent);
	    return ResponseEntity.ok()
		    .body(new ApiResponse(Constants.HTTP_CODE_200, Constants.LOGGED_OUT_SUCCESS, null));
	} catch (Exception e) {
	    return ResponseEntity.ok()
		    .body(new ErrorResponse(Constants.HTTP_CODE_401, new Date(), Constants.LOGGED_OUT_ERROR, null));
	}

    }
}
