package com.ecoland.model.request.system;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

@Data
public class FormIntroductionRequest {
    private Integer id;
    
    @NotEmpty(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 60)
    private String introductionName;
    
    @NotEmpty(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30)
    private String introductionShortName;
    
    @Size(max = 60)
    private String introductionNameKana;
    
    @Size(max = 7)
    private String postalCode;
    
    @Size(max = 60)
    private String address1;
    
    @Size(max = 60)
    private String address2;
    
    @Size(max = 60)
    private String address3;
    
    @Size(max = 13)
    private String tel;
    
    @Size(max = 13)
    private String fax;
    
    @NotNull
    private int payTiming;
    
    @NotNull
    private int payUnit;
    private int payAmount;
    private double payPercent;
    
    @NotNull
    private int discountUnit;
    
    private int discount;
    private double discountPercent;
    private int sortNo;
}
