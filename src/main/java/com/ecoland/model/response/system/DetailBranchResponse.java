package com.ecoland.model.response.system;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailBranchResponse {
    private Integer id;
    private Integer partnerId;
    private String branchName;
    private String branchShortName;
    private String postalCode;
    private String address1;
    private String address2;
    private String address3;
    private Integer inputCorpSite;
    private Integer delivCorpSite;
    private String tel;
    private String fax;
    private Date startDate;
    private Date endDate;
}
