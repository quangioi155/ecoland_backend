package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class SearchOemTypeRequest
 * 
 * @summary Request search oem type
 * 
 * @author ITSG - HoanNNC
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchOemTypeRequest extends PaginationRequest {
    private String oemName;
}
