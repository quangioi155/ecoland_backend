package com.ecoland.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecoland.security.UserDetailsImpl;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
public class CommonService {

    /**
     * 
     * @author thaotv@its-global.vn
     * @return info user login ex: id, username, role...
     * 
     */
    public UserDetails userDetails() {
	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	if (principal instanceof UserDetails) {
	    return ((UserDetails) principal);
	} else {
	    return null;
	}
    }

    public Integer idUserAccountLogin() {
	UserDetailsImpl userDetails = (UserDetailsImpl) userDetails();
	if (userDetails == null) {
	    return null;
	}
	return userDetails.getId().intValue();
    }

}
