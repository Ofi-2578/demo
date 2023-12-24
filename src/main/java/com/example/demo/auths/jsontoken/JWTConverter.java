package com.example.demo.auths.jsontoken;

import com.example.demo.auths.CustomAuth;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class JWTConverter implements ServerAuthenticationConverter {
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        List<String> s = exchange.getRequest().getHeaders().get("Authorization");
        assert s != null;
        String token = s.get(0);
        token = token.substring(7);
        return Mono.just(new CustomAuth(null,token,false));
    }
}
