package com.ecoland.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.ecoland.cache.LoggedOutJwtTokenCache;
import com.ecoland.event.OnUserLogoutSuccessEvent;
import com.ecoland.exception.InvalidTokenRequestException;
import com.ecoland.security.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Component
public class JwtTokenUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${ecoland.app.jwtSecret}")
    private String jwtSecret;

    @Value("${ecoland.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private LoggedOutJwtTokenCache log;

    public void setLoggedOutJwtTokenCache(LoggedOutJwtTokenCache lgogedOutJwtTokenCache) {
        this.log = lgogedOutJwtTokenCache;
    }

    public String generateJwtToken(String username) {

//	UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
	Claims claims = Jwts.claims().setSubject(username);
	return Jwts.builder().setClaims(claims).setIssuedAt(new Date())
		.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
		.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getUserNameFromJwtToken(String token) {
	return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
	try {
	    Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	    validateTokenIsNotForALoggedOutDevice(authToken);
	    return true;
	} catch (SignatureException e) {
	    logger.error("Invalid JWT signature: {}", e.getMessage());
	} catch (MalformedJwtException e) {
	    logger.error("Invalid JWT token: {}", e.getMessage());
	} catch (ExpiredJwtException e) {
	    logger.error("JWT token is expired: {}", e.getMessage());
	} catch (UnsupportedJwtException e) {
	    logger.error("JWT token is unsupported: {}", e.getMessage());
	} catch (IllegalArgumentException e) {
	    logger.error("JWT claims string is empty: {}", e.getMessage());
	}

	return false;
    }

    public Date getTokenExpiryFromJWT(String token) {
	Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

	return claims.getExpiration();
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
	OnUserLogoutSuccessEvent previouslyLoggedOutEvent = log.getLogoutEventForToken(authToken);
	if (previouslyLoggedOutEvent != null) {
	    String loginId = previouslyLoggedOutEvent.getLoginId();
	    Date logoutEventDate = previouslyLoggedOutEvent.getEventTime();
	    String errorMessage = String.format(
		    "Token corresponds to an already logged out user [%s] at [%s]. Please login again", loginId,
		    logoutEventDate);
	    throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
	}
    }
}
