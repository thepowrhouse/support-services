package com.fsd.supportservices.authserver.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
    private static final JsonParser OBJECT_MAPPER = JsonParserFactory.create();
    private static final String REFRESH_TOKEN = "refresh_token";

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomOAuth2AccessToken result = new CustomOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
        OAuth2Request request = authentication.getOAuth2Request();
        Map<String, String> requestParams;
        if (request.isRefresh()) {
            requestParams = request.getRefreshTokenRequest().getRequestParameters();
            try {
                Map<String, Object> claims = OBJECT_MAPPER
                        .parseMap(JwtHelper.decode(requestParams.get(REFRESH_TOKEN)).getClaims());
            } catch (IllegalArgumentException e) {
            }
        } else {
            requestParams = request.getRequestParameters();
        }
        String tokenId = result.getValue();
        if (!info.containsKey(TOKEN_ID)) {
            info.put(TOKEN_ID, tokenId);
        } else {
            tokenId = (String) info.get(TOKEN_ID);
        }
        result.setAdditionalInformation(info);
        result.setValue(encode(result, authentication));
        OAuth2RefreshToken refreshToken = result.getRefreshToken();
        if (refreshToken != null) {

            DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
            encodedRefreshToken.setValue(refreshToken.getValue());
            encodedRefreshToken.setExpiration(null);
            encodedRefreshToken.setScope(null); // Excluding scope list
            try {
                Map<String, Object> claims = OBJECT_MAPPER
                        .parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
                if (claims.containsKey(TOKEN_ID)) {
                    encodedRefreshToken.setValue(claims.get(TOKEN_ID).toString());
                }
            } catch (IllegalArgumentException e) {
            }
            Map<String, Object> refreshTokenInfo = new LinkedHashMap<String, Object>();
            refreshTokenInfo.put(TOKEN_ID, encodedRefreshToken.getValue());
            refreshTokenInfo.put(ACCESS_TOKEN_ID, tokenId);
            encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);
            DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(
                    encode(encodedRefreshToken, authentication));
            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                Date expiration = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration();
                encodedRefreshToken.setExpiration(expiration);
                token = new DefaultExpiringOAuth2RefreshToken(encode(encodedRefreshToken, authentication), expiration);
            }
            result.setRefreshToken(token);
        }
        return result;
    }
}
