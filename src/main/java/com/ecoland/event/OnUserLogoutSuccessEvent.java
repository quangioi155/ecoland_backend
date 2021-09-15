package com.ecoland.event;

import java.time.Instant;
import java.util.Date;

import org.springframework.context.ApplicationEvent;

import com.ecoland.model.request.LogOutRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Getter
@Setter
public class OnUserLogoutSuccessEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    private final String loginId;
    private final String token;
    private final transient LogOutRequest logOutRequest;
    private final Date eventTime;

    public OnUserLogoutSuccessEvent(String loginId, String token, LogOutRequest logOutRequest) {
	super(loginId);
	this.loginId = loginId;
	this.token = token;
	this.logOutRequest = logOutRequest;
	this.eventTime = Date.from(Instant.now());
    }
}
