package com.ecoland.model.request.system;

import com.ecoland.model.request.PaginationRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @class SearchIntroductionRequset
 * 
 * @summary Request search introduction
 * 
 * @author ITSG - HoanNNC
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchIntroductionRequest extends PaginationRequest {
    private String introductionName;
    private Integer payTiming;
    private Integer payUnit;
    private Integer discountUnit;
}
