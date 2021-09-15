package com.ecoland.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -8014749955090725783L;

    public RecordNotFoundException(String exception) {
	super(exception);
    }
}