package com.augmentedcooking.Models.Jwt;

import java.util.AbstractMap;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Claims extends AbstractMap<String, Object> implements io.jsonwebtoken.Claims {

    private final Map<String, Object> claims = new HashMap<>();

    public Claims() {
        super();
    }

    public Claims(Map<String, Object> claims) {
        super();
        this.claims.putAll(claims);
    }

    // @formatter:off
    @Override
    public Set<Map.Entry<String, Object>> entrySet() { return claims.entrySet(); }

    @Override public String getIssuer()     { return claims.get(ISSUER).toString(); }
    @Override public String getSubject()    { return claims.get(SUBJECT).toString(); }
    @Override public String getAudience()   { return claims.get(AUDIENCE).toString(); }
    @Override public Date   getExpiration() { return (Date) claims.get(EXPIRATION); }
    @Override public Date   getNotBefore()  { return (Date) claims.get(NOT_BEFORE); }
    @Override public Date   getIssuedAt()   { return (Date) claims.get(ISSUED_AT); }
    @Override public String getId()         { return claims.get(ID).toString(); }

    @Override public io.jsonwebtoken.Claims setIssuer(String iss)   { claims.put(ISSUER, iss); return this; }
    @Override public io.jsonwebtoken.Claims setSubject(String sub)  { claims.put(SUBJECT, sub); return this; }
    @Override public io.jsonwebtoken.Claims setAudience(String aud) { claims.put(AUDIENCE, aud); return this; }
    @Override public io.jsonwebtoken.Claims setExpiration(Date exp) { claims.put(EXPIRATION, exp); return this; }
    @Override public io.jsonwebtoken.Claims setNotBefore(Date nbf)  { claims.put(NOT_BEFORE, nbf); return this; }
    @Override public io.jsonwebtoken.Claims setIssuedAt(Date iat)   { claims.put(ISSUED_AT, iat); return this; }
    @Override public io.jsonwebtoken.Claims setId(String jti)       { claims.put(ID, jti); return this; }
    // @formatter:on

    @Override
    public <T> T get(String claimName, Class<T> requiredType) {
        Object claimValue = claims.get(claimName);
        return requiredType.cast(claimValue);
    }
}
