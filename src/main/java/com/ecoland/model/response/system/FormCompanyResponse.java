package com.ecoland.model.response.system;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class FormCompanyResponse
 * 
 * @summary Response detail partner
 * 
 * @author ITSG - HoanNNC
 */
@Data
@NoArgsConstructor
public class FormCompanyResponse {
    private Integer id;
    private String partnerName;
    private String address1;
    private String address2;
    private String address3;
    private String postalCode;
    private String tel;
    private String fax;
    private String mailAddress;
    private String startDate;
    private String endDate;
    private String managerName;
    private Boolean mainFlag;
}
