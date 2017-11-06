package com.fsd.supportservices.authserver.configurations;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@JsonSerialize(using = OAuth2AccessTokenJackson3Serializer.class)
public class CustomOAuth2AccessToken extends DefaultOAuth2AccessToken {
    private static final long serialVersionUID = 5064014460246617557L;

    public CustomOAuth2AccessToken(OAuth2AccessToken accessToken) {
        super(accessToken);
    }
}
