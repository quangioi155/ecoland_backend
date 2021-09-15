package com.ecoland.model.request.system;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.ecoland.common.Constants;

import lombok.Data;

/**
 * @class FormCompanyRequest
 * 
 * @summary create/edit partner
 * 
 * @author ITSG - HoanNNC
 */
@Data
public class FormCompanyRequest {

    private Integer id;
    @NotEmpty(message = Constants.VALIDATE_THE_FIELD)
    @Size(max = 30, message = Constants.VALIDATE_MAX_SIZE_30)
    private String partnerName;
    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_30)
    private String address1;
    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_30)
    private String address2;
    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_60)
    private String address3;
    @Size(max = 7, message = "No more than 7 character")
    private String postalCode;
    @Size(max = 13, message = "No more than 13 character")
    private String tel;
    @Size(max = 13, message = "No more than 13 character")
    private String fax;
    @Size(max = 60, message = Constants.VALIDATE_MAX_SIZE_60)
    private String mailAddress;
    private String startDate;
    private String endDate;
    private String managerName;
    private Boolean mainFlag;

}
