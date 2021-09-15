package com.ecoland.model.response;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class UserAccountListReponse {
    private Long id;
    private String partnerName;
    private String branchShortName;
    private String loginId;
    private String accountName;
    private String groupName;
    private String description;

    public UserAccountListReponse() {
    }
}
