package com.ecoland.model.response.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class SearchIntroductionResponse
 * 
 * @summary Response search introduction
 * 
 * @author ITSG - HoanNNC
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchIntroductionResponse {
    private Integer id;
    private String introductionName;
    private Integer payTiming;
    private Integer payUnit;
    private String payAmount;
    private Integer discountUnit;
    private String discount;
    private Integer sortNo;
}
