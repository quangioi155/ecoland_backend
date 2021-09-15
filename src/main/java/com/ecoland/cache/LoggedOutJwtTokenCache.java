package com.ecoland.cache;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecoland.event.OnUserLogoutSuccessEvent;
import com.ecoland.security.jwt.JwtTokenUtil;

import net.jodah.expiringmap.ExpiringMap;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Component
public class LoggedOutJwtTokenCache {
    private static final Logger logger = LoggerFactory.getLogger(LoggedOutJwtTokenCache.class);

    private ExpiringMap<String, OnUserLogoutSuccessEvent> tokenEventMap;

    @Autowired
    private JwtTokenUtil tokenProvider;

    @PostConstruct
    public void init() {
	tokenProvider.setLoggedOutJwtTokenCache(this);
    }

    @Autowired
    public LoggedOutJwtTokenCache(JwtTokenUtil tokenProvider) {
	this.tokenProvider = tokenProvider;
	this.tokenEventMap = ExpiringMap.builder().variableExpiration().maxSize(1000).build();
    }

    public void markLogoutEventForToken(OnUserLogoutSuccessEvent event) {
	String token = event.getToken();
	if (tokenEventMap.containsKey(token)) {
	    logger.info(
		    String.format("Log out token for user [%s] is already present in the cache", event.getLoginId()));

	} else {
	    Date tokenExpiryDate = tokenProvider.getTokenExpiryFromJWT(token);
	    long ttlForToken = getTTLForToken(tokenExpiryDate);
	    logger.info(String.format(
		    "Logout token cache set for [%s] with a TTL of [%s] seconds. Token is due expiry at [%s]",
		    event.getLoginId(), ttlForToken, tokenExpiryDate));
	    tokenEventMap.put(token, event, ttlForToken, TimeUnit.SECONDS);
	}
    }

    public OnUserLogoutSuccessEvent getLogoutEventForToken(String token) {
	return tokenEventMap.get(token);
    }

    private long getTTLForToken(Date date) {
	long secondAtExpiry = date.toInstant().getEpochSecond();
	long secondAtLogout = Instant.now().getEpochSecond();
	return Math.max(0, secondAtExpiry - secondAtLogout);
    }
}
