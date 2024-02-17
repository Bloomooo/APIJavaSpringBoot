package com.example.webcompany.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.webcompany.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.function.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import com.example.webcompany.entites.User;

@Service
public class JwtService {

    private final String ENCODEDKEY = "791042bc5e2e0fa97017e6692cdd81d8c7d91c0aa439c896d0bc9d436757e518";
    private UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public Boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public String findEmail(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public Map<String, String> generate(String email) {
        UserDetails userDetails = userService.loadUserByUsername(email);
        return this.generateJwt((User) userDetails);
    }

    private Map<String, String> generateJwt(User userDetails) {
        final long currentTime = System.currentTimeMillis();
        final long endTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> map = Map.of(
                Claims.EXPIRATION, endTime,
                "firstname", userDetails.getFirstname(),
                "lastname", userDetails.getLastname(),
                Claims.SUBJECT, userDetails.getEmail(),
                "password", userDetails.getPassword(),
                "firsttime", userDetails.isFirsttime());

        final String jwtMap = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(endTime))
                .claims(map)
                .signWith(getKey(),
                        SignatureAlgorithm.HS256)
                .compact();

        return Map.of("jwtMap", jwtMap);
    }

    private Key getKey() {
        final byte[] decode = Decoders.BASE64.decode(ENCODEDKEY);
        return Keys.hmacShaKeyFor(decode);
    }

    private Date getExpirationDate(String token) {
        return this.getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Decoders.BASE64.decode(ENCODEDKEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
