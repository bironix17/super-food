package ru.bironix.super_food.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.bironix.super_food.store.db.models.person.Person;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String authorizationHeader;

    @Value("${jwt.access_token.expiration}")
    private long validityInSeconds;

    private final String prefixBearer = "Bearer ";

    @Autowired
    public JwtTokenProvider(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createAccessToken(Person person) {
        Claims claims = Jwts.claims().setSubject(person.getPhoneNumber());
        claims.put("id", person.getId());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds * 1000);

        return prefixBearer.concat(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact());
    }

    public boolean validateToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        UserDetails details = userDetailsService.loadUserByUsername(getUsername(token));
        if (!details.isAccountNonLocked()) throw new LockedException("");
        return new UsernamePasswordAuthenticationToken(details, "", details.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Integer getPersonId(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody().get("id", Integer.class);
    }

    public String resolveToken(HttpServletRequest request) {
        var headerValue = request.getHeader(authorizationHeader);
        if (StringUtils.isBlank(headerValue)) return null;
        else return headerValue.substring(prefixBearer.length());
    }
}
