package com.example.demo.auths.jsonformlogin;

import com.example.demo.Message;
import com.example.demo.services.JsonAsBytesEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

public class LoginFailureHandler implements ServerAuthenticationFailureHandler {

    private void setResponseHeaders(ServerHttpResponse response){
        response.setRawStatusCode(405);
        response.getHeaders().add("Content-Type", "application/json");
    }

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        this.setResponseHeaders(webFilterExchange.getExchange().getResponse());
        String message = "username or password incorrect";
        byte[] b = JsonAsBytesEncoder.encode(new Message(message));
        DataBuffer db = webFilterExchange.getExchange().getResponse().bufferFactory().wrap(b);
        return webFilterExchange
                .getExchange()
                .getResponse()
                .writeWith(Mono.just(db));
    }
}