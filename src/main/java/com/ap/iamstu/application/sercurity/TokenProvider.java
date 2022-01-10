package com.ap.iamstu.application.sercurity;


import com.ap.iamstu.infrastructure.support.constant.BaseConstant;
import com.ap.iamstu.infrastructure.support.util.StringUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static com.ap.iamstu.infrastructure.support.constant.BaseConstant.EMAIL;
import static com.ap.iamstu.infrastructure.support.constant.BaseConstant.USER_ID;

@Component
@EnableConfigurationProperties(AuthenticationProperties.class)
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    private final AuthenticationProperties properties;
    private final TokenCacheService tokenCacheService;
    private KeyPair keyPair;
    private JWKSet jwkSet;
    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;
    private long emailTokenExpiresIn;

    public TokenProvider(AuthenticationProperties properties, TokenCacheService tokenCacheService) {
        this.properties = properties;
        this.tokenCacheService = tokenCacheService;
    }

    @Override
    public void afterPropertiesSet() {
        this.keyPair = keyPair(properties.getKeyStore(), properties.getKeyStorePassword(), properties.getKeyAlias());
        this.accessTokenExpiresIn = properties.getAccessTokenExpiresIn().toMillis();
        this.refreshTokenExpiresIn = properties.getRefreshTokenExpiresIn().toMillis();
        this.emailTokenExpiresIn = properties.getEmailTokenExpiresIn().toMillis();
        this.jwkSet = jwkSet();
    }

    public String createToken(Authentication authentication, String userId) {
        /*
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        */

        long now = Instant.now().toEpochMilli();
        Date validity = new Date(now + this.accessTokenExpiresIn);

        return Jwts.builder()
                .setSubject(authentication.getName())
                //.claim(AUTHORITIES_KEY, authorities)
                .claim(USER_ID, userId)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(validity)
                .compact();
    }

    public String createTokenSendEmail(String userId, String email) {
        long now = Instant.now().toEpochMilli();
        Date validity = new Date(now + this.emailTokenExpiresIn);

        return Jwts.builder()
                .setSubject(userId)
                .claim(EMAIL, email)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(validity)
                .compact();
    }

    public String validateEmailToken(String authToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(authToken)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException exception) {
            log.info("Expired JWT token.");
        } catch (Exception e) {
            log.warn("Invalid JWT signature.", e);
        }
        return null;
    }


    public String createRefreshToken(String userId) {
        long now = Instant.now().toEpochMilli();
        Date validity = new Date(now + this.refreshTokenExpiresIn);

        return Jwts.builder()
                .setSubject(userId)
                .claim(BaseConstant.AUTHORITY_TYPE, BaseConstant.REFRESH_TOKEN)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(validity)
                .compact();
    }

    public String createClientToken(String clientId) {
        long now = Instant.now().toEpochMilli();
        Date validity = new Date(now + this.accessTokenExpiresIn);
        return Jwts.builder()
                .setSubject(clientId)
                .claim(BaseConstant.AUTHORITY_TYPE, BaseConstant.CLIENT)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .setExpiration(validity)
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(authToken);
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return !tokenCacheService.isInvalidToken(authToken);
    }

    private KeyPair keyPair(String keyStore, String password, String alias) {
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(
                        new ClassPathResource(keyStore),
                        password.toCharArray());
        return keyStoreKeyFactory.getKeyPair(alias);
    }

    public JWKSet jwkSet() {
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) this.keyPair.getPublic()).keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(UUID.randomUUID().toString());
        return new JWKSet(builder.build());
    }

    /**
     * resolve token from request
     *
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }

        return null;
    }

    /**
     * invalid token and refreshtoken
     *
     * @param accessToken
     * @param refreshToken
     */
    public void invalidJwt(String accessToken, String refreshToken) {
        if (!StringUtil.isBlank(accessToken)) {
            tokenCacheService.invalidToken(accessToken);
        }
        if (!StringUtil.isBlank(refreshToken)) {
            tokenCacheService.invalidRefreshToken(refreshToken);
        }
    }

}
