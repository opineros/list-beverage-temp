package com.opineros.listbeverage.auth.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import com.opineros.listbeverage.auth.model.Role;
import java.util.Set;

@Component
public class JwtTokenProvider {
    private static final Key key = Keys.hmacShaKeyFor("my-super-secret-key-that-should-be-at-least-256-bits-long".getBytes());
    private static final long validityInMs = 3600000; // 1h

    public String createToken(String username, Set<Role> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMs);
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles.stream().map(Role::name).toList())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRoles(String token) {
        List<String> roles = (List<String>) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("roles");
        return roles.stream().map(Role::valueOf).toList();
    }
}
