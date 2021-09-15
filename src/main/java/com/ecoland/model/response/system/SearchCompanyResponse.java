package com.ecoland.model.response.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @class SearchCompanyResponse
 * 
 * @summary Response search partner
 * 
 * @author ITSG - HoanNNC
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompanyResponse {
    private int id;
    private String partnerName;
    private String postalCode;
    private String address;
    private String tel;
    private String fax;
}
