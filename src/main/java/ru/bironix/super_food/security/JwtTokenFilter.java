package ru.bironix.super_food.security;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                var authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null)
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
            ErrorAuthResponse.addError((HttpServletResponse) response, "Ошибочные данные токена");
        }
        filterChain.doFilter(request, response);
    }
}
