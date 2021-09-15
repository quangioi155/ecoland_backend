package com.ecoland.model.request.system;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author quannn@its-global.vn
 * 
 */
@Getter
@Setter
public class EditOrCreateOutsourceCompanyRequest {

    private Integer id;
    private String partnerName;
    private String name;
    private String nameKana;
    private String shortName;
    private String postalCode;
    private String address1;
    private String address2;
    private String address3;
    private String tel;
    private String fax;
}
