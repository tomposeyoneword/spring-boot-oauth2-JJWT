package com.payfone.oauth;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConstants
{
    public static final String PAYFONE = "Payfone";
    public static final int EXPIRATION = 60 * 60;
    public static final String TOKEN_URI = "/oauth/token**";
    public static final String TOKEN_TYPE_BEARER = "bearer";
    public static final String AUTHORITIES = "authorities";
    public static final String GRANT_TYPE_PASSWORD = "password";
}
