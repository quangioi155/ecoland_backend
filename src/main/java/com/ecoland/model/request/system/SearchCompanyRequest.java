package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class SearchCompanyRequest
 * 
 * @summary Request search company group
 * 
 * @author ITSG - HoanNNC
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchCompanyRequest extends PaginationRequest {
    private int id;
    private String partnerName;
    private boolean mainFlag;
}
