package com.ecoland.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ecoland.cache.LoggedOutJwtTokenCache;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Component
public class OnUserLogoutSuccessEventListener implements ApplicationListener<OnUserLogoutSuccessEvent> {

    private static final Logger logger = LoggerFactory.getLogger(OnUserLogoutSuccessEventListener.class);

    private final LoggedOutJwtTokenCache tokenCache;

    @Autowired
    public OnUserLogoutSuccessEventListener(LoggedOutJwtTokenCache tokenCache) {
	this.tokenCache = tokenCache;
    }

    @Override
    public void onApplicationEvent(OnUserLogoutSuccessEvent event) {
	if (null != event) {
	    logger.info(String.format("Log out success event received for user [%s]", event.getLoginId()));
	    tokenCache.markLogoutEventForToken(event);
	}
    }

}
