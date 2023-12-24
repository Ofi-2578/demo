package com.example.demo;

import com.example.demo.auths.jsonformlogin.*;
import com.example.demo.auths.jsontoken.*;
//import com.example.demo.services.MongoUserDetailsService;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig   {

    @Autowired
    UserRepository userRepository;
    private final AuthenticationWebFilter awf = customAuth();
    private final AuthenticationWebFilter jwt = jwtAuth();

    private AuthenticationWebFilter customAuth(){
        AuthenticationWebFilter awf = new AuthenticationWebFilter(new LoginAuthenticationManager(userRepository));
        awf.setRequiresAuthenticationMatcher(e -> ServerWebExchangeMatcher.MatchResult.match());
        awf.setServerAuthenticationConverter(new LoginConverter());
        awf.setAuthenticationSuccessHandler(new LoginSuccessHandler());
        awf.setAuthenticationFailureHandler(new LoginFailureHandler());
        return awf;
    }

    private AuthenticationWebFilter jwtAuth(){
        AuthenticationWebFilter awf = new AuthenticationWebFilter(new JWTAuthenticationManager(userRepository));
        awf.setServerAuthenticationConverter(new JWTConverter());
        awf.setAuthenticationFailureHandler(new JWTFailurHandler());
        awf.setAuthenticationSuccessHandler(new JWTSuccessHandler());
        awf.setRequiresAuthenticationMatcher(new JWTMatcher());
        return awf;
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain loginFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/login"))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .addFilterAt(customAuth(), SecurityWebFiltersOrder.AUTHENTICATION).build();
    }

    @Order(1)
    @Bean
    public SecurityWebFilterChain signupChain(ServerHttpSecurity http) throws Exception{
        return http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/signup"))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .build();
    }

    @Order(2)
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange
                        .anyExchange().authenticated())
        .addFilterAt(jwtAuth(), SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
