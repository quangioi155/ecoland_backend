package com.ecoland.model;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    // General status error
    private String status;

    // General time error
    private Date timestamp;

    // General error message about nature of error
    private String message;

    // Specific errors in API request processing
    private Map<String, String> details;

}
