package com.ecoland.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecoland.entity.UserAccounts;
import com.ecoland.model.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String accountName;
    private String accountNameKana;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String username, String accountName, String password, String accountNameKana,
	    Collection<? extends GrantedAuthority> authorities) {
	super();
	this.id = id;
	this.username = username;
	this.accountName = accountName;
	this.accountNameKana = accountNameKana;
	this.password = password;
	this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserAccounts user) {
	Set<String> roles = new HashSet<String>();
	if (user.getUserGroups().getContactCustomerFlag()) {
	    roles.add(ERole.CONTACT_CUSTOMER_FLAG.toString());
	}
	if (user.getUserGroups().getDriverFlag()) {
	    roles.add(ERole.DRIVER_FLAG.toString());
	}
	if (user.getUserGroups().getVehicleDispatchFlag()) {
	    roles.add(ERole.VEHICLE_DISPATCH_FLAG.toString());
	}
	if (user.getUserGroups().getZecFlag()) {
	    roles.add(ERole.ZEC_FLAG.toString());
	}
	if (user.getUserGroups().getManageFlag()) {
	    roles.add(ERole.MANAGE_FLAG.toString());
	}
	if (user.getUserGroups().getWarehouseFlag()) {
	    roles.add(ERole.WAREHOUSE_FLAG.toString());
	}
	if (user.getUserGroups().getSystemFlag()) {
	    roles.add(ERole.SYSTEM_FLAG.toString());
	}
	List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role))
		.collect(Collectors.toList());

	return new UserDetailsImpl(user.getId(), user.getLoginId(), user.getAccountName(), user.getLoginPassword(),
		user.getAccountNameKana(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return authorities;
    }

    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public String getUsername() {
	return username;
    }

    @Override
    public boolean isAccountNonExpired() {
	return true;
    }

    @Override
    public boolean isAccountNonLocked() {
	return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return true;
    }

    @Override
    public boolean isEnabled() {
	return true;
    }
}
