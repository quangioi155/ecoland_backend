package com.ecoland.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse {

    private String status;
    private String message;
    private Object item;

    public ApiResponse(String status, String message, Object item) {
	this.setStatus(status);
	this.setMessage(message);
	this.setItem(item);
    }

}