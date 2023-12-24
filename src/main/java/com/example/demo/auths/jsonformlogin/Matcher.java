package com.example.demo.auths.jsonformlogin;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class Matcher implements ServerWebExchangeMatcher {
    @Override
    public Mono<MatchResult> matches(ServerWebExchange exchange) {
        System.out.println((exchange.getRequest().getPath()));
        return MatchResult.match();
    }
}
