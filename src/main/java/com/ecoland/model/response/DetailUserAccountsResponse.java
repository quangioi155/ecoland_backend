package com.ecoland.model.response;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class DetailUserAccountsResponse {
    private Long id;
    private String loginId;
    private String loginPassword;
    private String employeeCd;
    private String accountName;
    private String accountNameKana;
    private Integer userGroupId;
    private Integer partnerId;
    private Integer branchId;
    private String description;
}
