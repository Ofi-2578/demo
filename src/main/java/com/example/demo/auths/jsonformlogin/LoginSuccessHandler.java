package com.example.demo.auths.jsonformlogin;

import com.example.demo.Message;
import com.example.demo.auths.JWT;
import com.example.demo.services.JsonAsBytesEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        WebFilterChain chain = webFilterExchange.getChain();
        chain
                .filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
        String token = JWT.create(authentication);
        byte[] tokenBytes = JsonAsBytesEncoder.encode(token);
        DataBuffer db = webFilterExchange.getExchange().getResponse().bufferFactory().wrap(tokenBytes);
        return exchange
                .getResponse()
                .writeWith(Mono.just(db));
    }
}
