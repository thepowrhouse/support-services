package com.fsd.supportservices.authserver.configurations;

import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import javax.crypto.KeyGenerator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EnableConfigurationProperties
public class CustomTokenEnhancer implements TokenEnhancer {
    private static final String FGL_SPORTS = "FSD Auth Token";
   // private static final String ROLE = "role";
   // private static final String DISPLAY_NAME = "displayName";
    private static final String ISSUED_AT = "issued_at";
    private static final String IAT = "iat";
    private static final String ISS = "iss";
    private static final String NAME = "name";
    private static final String AUTH_TOKEN = "authToken";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
   // private static final String FIRST_NAME = "firstName";
   // private static final String LAST_NAME = "lastName";

    public static final String HAZEL_CAST_USER_ROLES_STORE = "hazelCastUserRolesStore";
    public static final String HAZEL_CAST_USER_LOGIN_OBJECT_STORE = "hazelCastUserLoginObjectStore";

    private static final String STORE = "store";
    private static final String REFRESH_TOKEN = "refresh_token";

    private static final JsonParser OBJECT_MAPPER = JsonParserFactory.create();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*@Autowired
    private AuthService authService;*/

    private JSONParser parser = new JSONParser();

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Set<String> scope = new HashSet<String>();
        OAuth2Request request = authentication.getOAuth2Request();
        Map<String, String> requestParams;
        String storeId = "0";
        Boolean isRefreshTokenRequest = request.isRefresh();

        if (isRefreshTokenRequest) {
            requestParams = request.getRefreshTokenRequest().getRequestParameters();
            try {
                Map<String, Object> claims = OBJECT_MAPPER
                        .parseMap(JwtHelper.decode(requestParams.get(REFRESH_TOKEN)).getClaims());
                storeId = (String) claims.get(STORE);
            } catch (IllegalArgumentException e) {
            }
        } else {
            requestParams = request.getRequestParameters();
            storeId = requestParams.get(STORE);
        }

        final Integer roleId = getUserRoleId(key, storeId, isRefreshTokenRequest);
        final Map<String, Object> accessTokenInfo = new HashMap<>();
        final String firstName = cboAuthService.getFirstName();
        final String lastName = cboAuthService.getLastName();

        List<Details> details = app.getDetails().stream().filter(roles -> roles.getRoleID().equals(roleId))
                .collect(Collectors.toList());

        StringBuilder privileges = new StringBuilder();
        details.forEach(scopes -> {
            scopes.getPrivileges().forEach(priv -> {
                scope.add(priv);
                privileges.append(priv).append(" ");
            });
            accessTokenInfo.put(ROLE, scopes.getDescription());
        });
        logger.info("Scopes Assigned: {}", privileges);
        org.json.simple.JSONObject loginObject = getUserLoginObject(key, isRefreshTokenRequest);
        accessTokenInfo.put(STORE, storeId);
        accessTokenInfo.put(AUTH_TOKEN, loginObject.get(AUTH_TOKEN).toString());
        accessTokenInfo.put(FIRST_NAME, firstName);
        accessTokenInfo.put(LAST_NAME, lastName);
        accessTokenInfo.put(NAME, loginObject.get(DISPLAY_NAME).toString());
        accessTokenInfo.put(ISS, FGL_SPORTS);
        accessTokenInfo.put(IAT, System.currentTimeMillis() / 1000);
        accessTokenInfo.put(ISSUED_AT,
                DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneOffset.UTC).format(Instant.now()));
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
        defaultOAuth2AccessToken.setScope(scope);
        defaultOAuth2AccessToken.setAdditionalInformation(accessTokenInfo);

        return accessToken;
    }

    private Integer getUserRoleId(Key key, String storeId, Boolean isRefreshTokenRequest) {
        // Check in hazelcast first and populate if its not present only if it
        // is a refresh token call
        Integer userRoles;
        if (isRefreshTokenRequest && cachedTokenStore.getMap(HAZEL_CAST_USER_ROLES_STORE).containsKey(key)) {
            userRoles = (Integer) cachedTokenStore.getMap(HAZEL_CAST_USER_ROLES_STORE).get(key);
            logger.info("User roles found in Cache for key: {} is {} ", key, userRoles);
            return userRoles;
        }

        userRoles = cboAuthService.getUserRole(storeId);
        cachedTokenStore.getMap(HAZEL_CAST_USER_ROLES_STORE).put(key, userRoles, 0, TimeUnit.SECONDS);
        return userRoles;
    }

    private org.json.simple.JSONObject getUserLoginObject(Key key, Boolean isRefreshTokenRequest) {
        // Check in hazelcast first and populate if its not present only if it
        // is a refresh token call
        logger.info("Fetching UserLoginObject for key : {}", key);
        org.json.simple.JSONObject loginObject;
        if (isRefreshTokenRequest && cachedTokenStore.getMap(HAZEL_CAST_USER_LOGIN_OBJECT_STORE).containsKey(key)) {
            String cachedLoginObject = cachedTokenStore.getMap(HAZEL_CAST_USER_LOGIN_OBJECT_STORE).get(key).toString();
            try {
                loginObject = (org.json.simple.JSONObject) parser.parse(cachedLoginObject);
                logger.info("UserLoginObject found in Cache for key: {} is {} ", key, loginObject);
                return loginObject;
            } catch (ParseException e) {
                logger.error("Json parser error ", e);
            }
        }

        loginObject = cboAuthService.getLoginObject();
        cachedTokenStore.getMap(HAZEL_CAST_USER_LOGIN_OBJECT_STORE).put(key, loginObject, 0, TimeUnit.SECONDS);
        return loginObject;
    }
}
