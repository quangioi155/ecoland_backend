package com.ecoland.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.entity.UserAccounts;
import com.ecoland.repository.UserAccountsRepository;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserAccountsRepository userRepository;

    @Cacheable(cacheNames = "loadUserName")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	logger.info("Load by user name");
	UserAccounts user = userRepository.findByLoginIdAndDeleteFlag(username, 0)
		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
	return UserDetailsImpl.build(user);
    }
}
