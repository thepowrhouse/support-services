package com.fsd.supportservices.authserver.configurations;

import com.fsd.supportservices.authserver.services.AuthService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {
    @Autowired
    private AuthService authService;

    @Autowired
    private Environment env;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {		//OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        String roleId = null;
        JSONArray scope = new JSONArray();

        roleId =  new JSONObject(authService.getUserRole()).get("groupId").toString();

        final Map<String, Object> accessTokenInfo = new HashMap<>();
        if (env.getProperty("Roles.Privilege").equals("Admin")){
            scope.add("ADMIN");
        }
        if (env.getProperty("Roles.Privilege").equals("User")){
            scope.add("USER");
        }


        accessTokenInfo.put("scope", scope);
        accessTokenInfo.put("roles", env.getProperty("Roles.ID-"+roleId+".Description"));
        accessTokenInfo.put("iss", "FSD Auth Service");
        accessTokenInfo.put("iat", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                .withZone(OffsetDateTime.now().getOffset())
                .format(Instant.now()));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(accessTokenInfo);

        return accessToken;
    }
}
