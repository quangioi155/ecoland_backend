package com.ecoland.model.response.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class FormIntroductionResponse
 * 
 * @summary Response detail introduction
 * 
 * @author ITSG - HoanNNC
 */
@Data
@NoArgsConstructor
public class FormIntroductionResponse {
    private Integer id;
    private String introductionName;
    private String introductionShortName;
    private String introductionNameKana;
    private String postalCode;
    private String address1;
    private String address2;
    private String address3;
    private String tel;
    private String fax;
    private int payTiming;
    private int payUnit;
    private int payAmount;
    private double payPercent;
    private int discountUnit;
    private int discount;
    private double discountPercent;
    private int sortNo;
}
