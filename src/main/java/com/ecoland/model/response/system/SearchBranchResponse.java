package com.ecoland.model.response.system;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchBranchResponse {

    private Integer id;
    private String partnerName;
    private String branchName;
    private String branchShortName;
    private String postalCode;
    private String address;
    private Integer inputCorpSite;
    private Integer delivCorpSite;
    private String tel;
    private String fax;
    private Date startDate;
    private Date endDate;

}
