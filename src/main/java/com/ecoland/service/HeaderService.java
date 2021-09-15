package com.ecoland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecoland.common.CommonService;
import com.ecoland.entity.UserAccounts;
import com.ecoland.model.response.HeaderInfoUserLoginResponse;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
public class HeaderService {

    @Autowired
    private UserAccountsService userAccountsService;

    @Autowired
    private CommonService commonService;
    
    /**
     * 
     * @author thaotv@its-global.vn
     * @return info user login (partnersName, branchShortName, accountName)
     */
    @Cacheable(value = "infoUserLogin")
    public HeaderInfoUserLoginResponse infoUserLogin() {
	UserDetails userDetails = commonService.userDetails();
	HeaderInfoUserLoginResponse headerInfoUserLoginResponse = new HeaderInfoUserLoginResponse();
	if (userDetails != null) {
	    Optional<UserAccounts> userAccount = userAccountsService.findByUser(userDetails.getUsername());
	    if (!userAccount.isEmpty()) {
		headerInfoUserLoginResponse.setAccountName(userAccount.get().getAccountName());
		headerInfoUserLoginResponse.setBranchShortName(userAccount.get().getBranches().getBranchShortName());
		headerInfoUserLoginResponse.setPartnersName(userAccount.get().getPartners().getPartnerName());
	    }
	}
	return headerInfoUserLoginResponse;
    }

    /**
     * 
     * @author thaotv@its-global.vn
     * @param ecoNo and tel
     */
    public void searchHeader(Integer ecoNo, Integer tel) {

    }
}
