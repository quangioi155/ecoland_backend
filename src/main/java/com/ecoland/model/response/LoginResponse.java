package com.ecoland.model.response;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class LoginResponse {
    private String username;
    private String token;
    private List<String> roles;

    public LoginResponse(String username, String token, List<String> roles) {
	super();
	this.username = username;
	this.token = token;
	this.roles = roles;
    }

    public LoginResponse() {
    }

}
