package com.example.demo.auths.jsontoken;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JWTMatcher implements ServerWebExchangeMatcher {
    @Override
    public Mono<ServerWebExchangeMatcher.MatchResult> matches(ServerWebExchange exchange) {
        System.out.println((exchange.getRequest().getPath()));
        return ServerWebExchangeMatcher.MatchResult.match();
    }
}