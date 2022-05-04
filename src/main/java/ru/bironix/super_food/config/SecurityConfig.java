package ru.bironix.super_food.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bironix.super_food.security.AccessDeniedHandlerJwt;
import ru.bironix.super_food.security.AuthenticationEntryPointJwt;
import ru.bironix.super_food.security.JwtChainConfigurer;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true) //TODO
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtChainConfigurer jwtChainConfigurer;

    @Autowired
    private AuthenticationEntryPointJwt authenticationEntryPointJwt;
    @Autowired
    private AccessDeniedHandlerJwt accessDeniedHandlerJwt;

    @Autowired
    public SecurityConfig(JwtChainConfigurer jwtChainConfigurer) {
        this.jwtChainConfigurer = jwtChainConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()

                .antMatchers("/view/**").permitAll()
                .antMatchers("/styles/**").permitAll()

                .antMatchers("/auth/register").permitAll()
                .antMatchers("/auth/login").permitAll()

                .anyRequest()
                .authenticated()
                .and()

                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointJwt).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandlerJwt).and()

                .apply(jwtChainConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}